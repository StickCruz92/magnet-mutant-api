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

import com.mutant.api.dto.DnaDto;
import com.mutant.api.repository.AdnRepository;
import com.mutant.api.service.ServiceMuntant;
import com.mutant.api.util.AdnFactory;
import com.mutant.api.util.Response;

import static com.mutant.api.util.AdnConstants.MUTANT_FALSE;
import static com.mutant.api.util.AdnConstants.MUTANT_TRUE;
import static com.mutant.api.util.MessageConstants.DEM001;
import static com.mutant.api.util.MessageConstants.UIM001;
import static com.mutant.api.util.MessageConstants.UIM002;
import static com.mutant.api.util.MessageConstants.UIM003;
import static com.mutant.api.util.MessageConstants.UIM004;
import static com.mutant.api.util.MessageConstants.UIM010;
import static com.mutant.api.util.LoggerConstants.EMPTY_STRING;

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
		assertEquals(UIM004, response.getMessage());
	}

	
	@Test
	@Order(2)
	void getAdngetStatsNull() throws Exception {

		Response response = this.serviceMuntant.getStats();
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals(UIM004, response.getMessage());
	}

	@Test
	@Order(3)
	void validateMutantSucess() throws Exception {
		DnaDto adnDto = new DnaDto();
		Response responseFinal = new Response(HttpStatus.OK.value(), UIM001, DEM001, MUTANT_TRUE.toString());
		adnDto = AdnFactory.getAnyAdnDtoMutant();
		System.out.println(adnDto.getDna());
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals(response, responseFinal);
	}

	@Test
	@Order(4)
	void validateHumanSucess() throws Exception {

		Response responseFinal = new Response(HttpStatus.FORBIDDEN.value(), UIM002, DEM001, MUTANT_FALSE.toString());
		DnaDto adnDto = AdnFactory.getAnyAdnDtoHuman();

		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
		assertEquals(UIM002, response.getMessage());
		assertEquals(DEM001, response.getMessageDeveloper());
		assertEquals(response, responseFinal);
	}


	@Test
	@Order(7)
	void validateMutantForbidden() throws Exception {

		DnaDto adnDto = AdnFactory.getAnyAdnDtoMutant();
		this.serviceMuntant.validateMutant(adnDto);
		
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.CONFLICT.value());
		assertEquals(response.getMessage(), UIM003);
	}
	
	
	@Test
	@Order(8)
	void getListAdnSucenss() throws Exception {
		DnaDto adnOne = AdnFactory.getAnyAdnDtoMutant();
		this.serviceMuntant.validateMutant(adnOne);
		
		DnaDto adnTwo = AdnFactory.getAnyAdnDtoHuman();
		this.serviceMuntant.validateMutant(adnTwo);
		
		Response response = this.serviceMuntant.getListAdn();
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals("consulta exitosa", response.getMessage());
	}
	

	@Test
	@Order(9)
	void getAdngetStatsSucenss() throws Exception {
		DnaDto adnOne = AdnFactory.getAnyAdnDtoMutant();
		this.serviceMuntant.validateMutant(adnOne);
		
		DnaDto adnTwo = AdnFactory.getAnyAdnDtoHuman();
		this.serviceMuntant.validateMutant(adnTwo);
		
		Response response = this.serviceMuntant.getStats();
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals("consulta exitosa", response.getMessage());
	}
	
	@Test
	@Order(10)
	void getAdnvalidateMutantException() throws Exception {
		DnaDto adnDto = new DnaDto();
		String[] dna = { "ATGCGA","CAGT","TTATGT","AGAAGG","CCCCTATCACTQ" };
		adnDto.setDna(dna);
		Response response = this.serviceMuntant.validateMutant(adnDto);
		assertEquals(response.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	
}
