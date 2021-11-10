package com.mutant.api.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.repository.AdnRepository;
import com.mutant.api.service.ServiceMuntant;
import com.mutant.api.util.AdnFactory;
import com.mutant.api.util.Response;

import static com.mutant.api.util.AdnConstants.MUTANT_FALSE;
import static com.mutant.api.util.AdnConstants.MUTANT_TRUE;

@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(OrderAnnotation.class)
class MutantServiceTest {
	
	/** Objeto que realizar las operaciones de negocio */
	@Autowired
	private ServiceMuntant serviceMuntant;

	@Autowired
	private AdnRepository adnRepository;

	@BeforeEach
	public void setUp() {
		adnRepository.deleteAll();
	}

	@Test
	@Order(1)
	void getListAdnNoContent() throws Exception {

		Response response = this.serviceMuntant.getListAdn();
		assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
		assertEquals("consulta exitosa", response.getMessage());

	}

	
	@Test
	@Order(2)
	void getAdngetStatsNull() throws Exception {

		Response response = this.serviceMuntant.getStats();
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals("consulta exitosa", response.getMessage());

	}

	@Test
	@Order(3)
	void validateMutantSucess() throws Exception {

		Response responseFinal = new Response(HttpStatus.OK.value(), "consulta exitosa", MUTANT_TRUE.toString());
		AdnDto adnDto = AdnFactory.getAnyAdnDtoMutant();
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals(response, responseFinal);

	}

	@Test
	@Order(4)
	void validateHumanSucess() throws Exception {

		Response responseFinal = new Response(HttpStatus.FORBIDDEN.value(), "consulta exitosa", MUTANT_FALSE.toString());
		AdnDto adnDto = AdnFactory.getAnyAdnDtoHuman();

		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
		assertEquals("consulta exitosa", response.getMessage());
		assertEquals(response, responseFinal);

	}

	@Test
	@Order(5)
	void validateisMutantTrue() throws Exception {

		AdnDto adnDto = AdnFactory.getAnyAdnDtoMutant();
		boolean response = this.serviceMuntant.isMutant(adnDto.getDna());
		assertEquals(response, MUTANT_TRUE);

	}

	@Test
	@Order(6)
	void validateisMutantFalse() throws Exception {

		AdnDto adnDto = AdnFactory.getAnyAdnDtoHuman();
		boolean response = this.serviceMuntant.isMutant(adnDto.getDna());
		assertEquals(response, MUTANT_FALSE);

	}
	

	@Test
	@Order(7)
	void validateMutantForbidden() throws Exception {

		AdnDto adnDto = AdnFactory.getAnyAdnDtoMutant();
		this.serviceMuntant.validateMutant(adnDto);
		
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.CONFLICT.value());
		assertEquals(response.getMessage(), "La cadena de ADN ya fue analizada");

	}
	
	@Test
	@Order(8)
	void validateMutantExist() throws Exception {

		Response responseFinal = new Response(HttpStatus.CONFLICT.value(), "La cadena de ADN es un campo obligatorio");
		AdnDto adnDto = new AdnDto();
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.CONFLICT.value());
		assertEquals(response, responseFinal);

	}
	
	@Test
	@Order(10)
	void getListAdnSucenss() throws Exception {
		AdnDto adnOne = AdnFactory.getAnyAdnDtoMutant();
		this.serviceMuntant.validateMutant(adnOne);
		
		AdnDto adnTwo = AdnFactory.getAnyAdnDtoHuman();
		this.serviceMuntant.validateMutant(adnTwo);
		
		Response response = this.serviceMuntant.getListAdn();
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals("consulta exitosa", response.getMessage());

	}
	

	@Test
	@Order(11)
	void getAdngetStatsSucenss() throws Exception {
		AdnDto adnOne = AdnFactory.getAnyAdnDtoMutant();
		this.serviceMuntant.validateMutant(adnOne);
		
		AdnDto adnTwo = AdnFactory.getAnyAdnDtoHuman();
		this.serviceMuntant.validateMutant(adnTwo);
		
		Response response = this.serviceMuntant.getStats();
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals("consulta exitosa", response.getMessage());

	}
	
	@Test
	@Order(12)
	void getAdnvalidateMutantException() throws Exception {
		AdnDto adnDto = new AdnDto();
		String[] dna = { "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTATCACTQ" };
		adnDto.setDna(dna);
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
	
	
}
