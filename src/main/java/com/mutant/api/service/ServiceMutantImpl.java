package com.mutant.api.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.dto.DnaDTO;
import com.mutant.api.dto.StatiAdnDto;
import com.mutant.api.mapper.AdnMapper;
import com.mutant.api.model.Adn;
import com.mutant.api.repository.AdnRepository;
import com.mutant.api.util.AdnEnum;
import com.mutant.api.util.Response;

import static com.mutant.api.util.AdnConstants.MUTANT_FALSE;
import static com.mutant.api.util.AdnConstants.MUTANT_TRUE;

@Service
public class ServiceMutantImpl implements ServiceMuntant {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMutantImpl.class);

	private AdnRepository adnRepository;

	private AdnMapper adnMapper;

	@Autowired
	private ModelMapper modelMapper;

	public ServiceMutantImpl(AdnRepository adnRepository, AdnMapper adnMapper) {
		this.adnRepository = adnRepository;
		this.adnMapper = adnMapper;
	}

	@Override
	public Response validateMutant(AdnDto dna) {
		boolean mutant = MUTANT_FALSE;
		Response response = null;

		try {
			if (dna.getDna() != null) {

				if (!(existsByAdnChain(Arrays.toString(dna.getDna())))) {

					mutant = isMutant(dna.getDna());
					if (mutant) {
						adnRepository.save(adnMapper.createAdnTemplate(MUTANT_TRUE, dna));
						response = new Response(HttpStatus.OK.value(), "consulta exitosa", MUTANT_TRUE.toString());
					} else {
						adnRepository.save(adnMapper.createAdnTemplate(MUTANT_FALSE, dna));
						response = new Response(HttpStatus.FORBIDDEN.value(), "consulta exitosa",
								MUTANT_FALSE.toString());
					}

				} else {

					response = new Response(HttpStatus.CONFLICT.value(), "La cadena de ADN ya fue analizada",
							getFindByAdnChain(Arrays.toString(dna.getDna())));
				}

			} else {
				response = new Response(HttpStatus.CONFLICT.value(), "La cadena de ADN es un campo obligatorio");

			}
		} catch (Exception e) {
			response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al procesar su adn: "
					+ e.getStackTrace().toString() + "  " + "Error message: " + e.getMessage().toString());
			LOGGER.error("Error [validateMutant]" + e.getStackTrace().toString() + "  " + "Error message: "
					+ e.getMessage().toString());

		}

		return response;
	}
	
	@Override
	public Response getStats() {
		Response response = null;
		try {
			response = new Response(HttpStatus.OK.value(), "consulta exitosa", getStatic());
		} catch (Exception e) {
			response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error listar los adns: "
					+ e.getStackTrace().toString() + "  " + "Error message: " + e.getMessage().toString());
			LOGGER.error("Error [getStats]" + e.getStackTrace().toString() + "  " + "Error message: "
					+ e.getMessage().toString());
		}

		return response;
	}

	@Override
	public Response getListAdn() {
		Response response = null;
		List<DnaDTO> adns = null;

		try {

			adns = adnRepository.findAll().stream().map(dnaDto -> modelMapper.map(dnaDto, DnaDTO.class))
					.collect(Collectors.toList());

			if (!adns.isEmpty()) {
				response = new Response(HttpStatus.OK.value(), "consulta exitosa", adns);
			} else {
				response = new Response(HttpStatus.NO_CONTENT.value(), "consulta exitosa", adns);
			}
		} catch (Exception e) {
			response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error listar los adns: "
					+ e.getStackTrace().toString() + "  " + "Error message: " + e.getMessage().toString());
			LOGGER.error("Error [getListAdn]" + e.getStackTrace().toString() + "  " + "Error message: "
					+ e.getMessage().toString());
		}

		return response;
	}

	@Override
	public boolean isMutant(String[] dna) {

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

	@Override
	public boolean existsByAdnChain(String dnaChain) {
		return adnRepository.existsAdnByAdnChain(dnaChain);
	}

	private StatiAdnDto getStatic() {

		var statiAdnDto = new StatiAdnDto();
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

	@Override
	public List<Adn> getFindByAdnChain(String adn) {
		return adnRepository.findByAdnChain(adn);
	}

}
