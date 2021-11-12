package com.mutant.api.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.dto.DnaDto;
import com.mutant.api.dto.StaticAdnDto;
import com.mutant.api.mapper.AdnMapper;
import com.mutant.api.model.Adn;
import com.mutant.api.repository.AdnRepository;
import com.mutant.api.util.AdnEnum;
import com.mutant.api.util.MutantValidation;
import com.mutant.api.util.Response;

import static com.mutant.api.util.AdnConstants.MUTANT_FALSE;
import static com.mutant.api.util.AdnConstants.MUTANT_TRUE;
import static com.mutant.api.util.LoggerConstants.LOG_VALIDATE_MUTANT_ERROR;
import static com.mutant.api.util.LoggerConstants.LOG_GE_STATS_ERROR;
import static com.mutant.api.util.LoggerConstants.LOG_GET_LIST_ADN_ERROR;
import static com.mutant.api.util.LoggerConstants.EMPTY_STRING;

import static com.mutant.api.util.MessageConstants.DEM001;
import static com.mutant.api.util.MessageConstants.UIM004;
import static com.mutant.api.util.MessageConstants.DEM010;

import static com.mutant.api.util.MessageConstants.UIM001;
import static com.mutant.api.util.MessageConstants.UIM002;
import static com.mutant.api.util.MessageConstants.UIM003;
import static com.mutant.api.util.MessageConstants.UIM010;

/**
 * Clase se encarga de implementar la logica que necesita Magento para su ejercito de mutantes
 * 
 * @author stick.cruz1992@gmail.com
 * 
 */

@Service
public class ServiceMutantImpl implements ServiceMuntant {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMutantImpl.class);

	/** Objeto de acceso a la capa de datos para la entidad de los ADN */
	private AdnRepository adnRepository;

	private AdnMapper adnMapper;
	
	/** Objeto de validacion de datos */
	private MutantValidation mutantValidation;

    /**
     * Metodo constructor
     * 
     */
	@Autowired
	public ServiceMutantImpl(AdnRepository adnRepository, AdnMapper adnMapper, MutantValidation mutantValidation) {
		this.adnRepository = adnRepository;
		this.adnMapper = adnMapper;
		this.mutantValidation = mutantValidation;
	}
	
    /**
     * Permite listar todos los ADN procesados por Magneto
     * 
     * @see ServiceMuntant#getListAdn()
     */
	@Override
	public Response getListAdn() {
		Response response = null;
		List<AdnDto> adns = null;

		try {
			adns = adnRepository.findAll().stream().map(dna -> adnMapper.createAdnTemplateAdnDto(dna))
					.collect(Collectors.toList());

			if (!adns.isEmpty()) {
				response = new Response(HttpStatus.OK.value(), UIM004, DEM001, adns);
			} else {
				response = new Response(HttpStatus.NO_CONTENT.value(), UIM004, DEM001, adns);
			}
		} catch (Exception e) {

			response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), DEM010,
					LOG_GET_LIST_ADN_ERROR + e.getStackTrace().toString() + " - " + e.getMessage().toString(),
					EMPTY_STRING);
		}

		return response;
	}


    /**
     * Permite validar si eres un Humano o un Mutante
     * 
     *  @see ServiceMuntant#validateMutant()
     *
     * @param DnaDto
     * @return
     */
	@Override
	public Response validateMutant(DnaDto dna){
		LOGGER.debug("Init validateMutant with request: {}", dna);
		boolean mutant = MUTANT_FALSE;
		AdnDto adnDto = new AdnDto();
		Response response = null;
		List<AdnDto> adnsDto = null;

		try {
			this.mutantValidation.verifyObjectData(dna);

			if (!(existsByAdnChain(Arrays.toString(dna.getDna())))) {

				mutant = isMutant(dna.getDna());
				adnDto.setDnaChain(Arrays.toString(dna.getDna()));

				if (mutant) {
					adnRepository.save(adnMapper.createAdnDtoTemplateAdn(adnDto, MUTANT_TRUE));
					response = new Response(HttpStatus.OK.value(), UIM001, DEM001, MUTANT_TRUE.toString());
				} else {
					adnRepository.save(adnMapper.createAdnDtoTemplateAdn(adnDto, MUTANT_FALSE));
					response = new Response(HttpStatus.FORBIDDEN.value(), UIM002, DEM001, MUTANT_FALSE.toString());
				}
			} else {

				adnsDto = getFindByAdnChain(Arrays.toString(dna.getDna())).stream()
						.map(d -> adnMapper.createAdnTemplateAdnDto(d)).collect(Collectors.toList());
				response = new Response(HttpStatus.CONFLICT.value(), UIM003, DEM001, adnsDto);
			}

		} catch (Exception e) {

			response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), UIM010,
					LOG_VALIDATE_MUTANT_ERROR + e.getStackTrace().toString() + " - " + e.getMessage().toString(),
					EMPTY_STRING);
		}

		return response;
	}

    /**
     * Permite obtener las estadisticas de los humanos y los mutantes analizados
     * 
     * @see ServiceMuntant#getStats()
     */

	@Override
	public Response getStats() {
		Response response = null;
		try {
			response = new Response(HttpStatus.OK.value(), UIM004, DEM001, getStatic());
		} catch (Exception e) {

			response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), UIM010,
					LOG_GE_STATS_ERROR + e.getStackTrace().toString() + " - " + e.getMessage().toString(),
					EMPTY_STRING);
		}

		return response;
	}

	@Override
	public boolean existsByAdnChain(String dnaChain) {
		return adnRepository.existsAdnByAdnChain(dnaChain);
	}

	@Override
	public List<Adn> getFindByAdnChain(String adn) {
		return adnRepository.findByAdnChain(adn);
	}

	private StaticAdnDto getStatic() {

		var statiAdnDto = new StaticAdnDto();
		List<Adn> adns = adnRepository.findAll();

		Map<Boolean, Long> counting = adns.stream()
				.collect(Collectors.groupingBy(Adn::isMutant, Collectors.counting()));

		if (!counting.isEmpty()) {
			statiAdnDto.setCountHumanDna(counting.get(MUTANT_FALSE).byteValue());
			statiAdnDto.setCountMutantDna(counting.get(MUTANT_TRUE).byteValue());
			statiAdnDto
					.setRatio(new BigDecimal((double) statiAdnDto.getCountMutantDna() / statiAdnDto.getCountHumanDna())
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		return statiAdnDto;

	}

	private boolean isMutant(String[] dna) {

		boolean ismutant = MUTANT_FALSE;
		var dnaChainMutant = 0;

		LOGGER.debug("Iniciando isMutant");

		char[][] dnaStructure = convertObjectToMatrix(dna);

		for (AdnEnum adn : AdnEnum.values()) {
			if (lookForMutantDna(adn.toString(), dnaStructure) > 0) {
				dnaChainMutant++;
			}
		}

		if (dnaChainMutant > 1) {
			ismutant = MUTANT_TRUE;
		}

		return ismutant;
	}

	private char[][] convertObjectToMatrix(String[] dnaStructure) {

		int rows = dnaStructure.length;
		int columns = dnaStructure[0].length();

		char[][] matrix = new char[rows][columns];

		for (int i = 0; i < dnaStructure.length; i++) {
			for (int k = 0; k < dnaStructure[i].length(); k++) {
				matrix[i][k] = dnaStructure[i].charAt(k);
			}
		}

		return matrix;
	}

	private int lookForMutantDna(String word, char[][] dnaStructure) {

		var dnaChain = 0;

		for (int fila = 0; fila < dnaStructure.length; fila++) {
			for (int columns = 0; columns < dnaStructure[fila].length; columns++) {

				// busca derecha
				if (columns + (word.length() - 1) < dnaStructure[fila].length) {
					boolean found = true;

					for (int letters = 0; letters < word.length(); letters++) {
						if (word.charAt(letters) != dnaStructure[fila][columns + letters]) {
							found = false;
							break;
						}
					}
					if (found) {
						dnaChain++;
					}
				}

				// busca arriba
				if (fila - (word.length() - 1) >= 0) {
					boolean found = true;

					for (int letters = 0; letters < word.length(); letters++) {
						if (word.charAt(letters) != dnaStructure[fila - letters][columns]) {
							found = false;
							break;
						}
					}
					if (found) {
						dnaChain++;
					}
				}

				// diagonal abajo derecha
				if ((fila + (word.length() - 1) < dnaStructure.length)
						&& (columns + (word.length() - 1) <= dnaStructure[fila].length)) {
					boolean found = true;

					for (int letters = 0; letters < word.length(); letters++) {
						if (word.charAt(letters) != dnaStructure[fila + letters][columns + letters]) {
							found = false;
							break;
						}
					}
					if (found) {
						dnaChain++;
					}
				}

				// diagonal abajo izquierda
				if ((fila + (word.length() - 1) < dnaStructure.length) && (columns - (word.length() - 1) >= 0)) {
					boolean found = true;

					for (int letters = 0; letters < word.length(); letters++) {
						if (word.charAt(letters) != dnaStructure[fila + letters][columns - letters]) {
							found = false;
							break;
						}
					}
					if (found) {
						dnaChain++;
					}
				}
			}
		}

		return dnaChain;
	}

}
