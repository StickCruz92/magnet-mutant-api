package com.mutant.api.unit;

import static org.mockito.ArgumentMatchers.any;
import static com.mutant.api.util.AdnConstants.MUTANT_FALSE;
import static com.mutant.api.util.AdnConstants.MUTANT_TRUE;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.mutant.api.dto.AdnDto;
import com.mutant.api.dto.StatiAdnDto;
import com.mutant.api.model.Adn;
import com.mutant.api.rest.MutantRest;
import com.mutant.api.service.ServiceMuntant;
import com.mutant.api.util.AdnFactory;
import com.mutant.api.util.Response;

@SpringBootTest
@Profile("dev")
class MutantRestTest {

	private static final String RESPONSE_STATUS = "$.status";

	/** Objeto para realizar el llamado a las Apis mockeadas */
	private MockMvc restMock;

	/** Objeto para serializacion/deserializacion de objetos */
	private final Gson gson = new Gson();

	/** Objeto que realizar las operaciones de negocio */
	@MockBean
	private ServiceMuntant serviceMuntant;

	/**
	 * Permite inicializar los objetos requeridos para la ejecucion de pruebas
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.restMock = MockMvcBuilders.standaloneSetup(new MutantRest(this.serviceMuntant)).build();
	}

	@Test
	void getListAdnSuccess() throws Exception {
		Response response = null;
		Adn adnOne = AdnFactory.getAnyAdn();
		Adn adnSecond = AdnFactory.getAnyAdn();

		List<Adn> iterableRersp = new ArrayList<Adn>();
		iterableRersp.add(adnOne);
		iterableRersp.add(adnSecond);

		response = new Response(HttpStatus.OK.value(), "consulta exitosa", iterableRersp);
		when(this.serviceMuntant.getListAdn()).thenReturn(response);
		restMock.perform(get(URI.create("/api/v1/muntant/")).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(response).getBytes()).characterEncoding("utf-8")).andDo(print())//;
				.andExpect(status().isOk())//;
				.andExpect(jsonPath(RESPONSE_STATUS).value(HttpStatus.OK.value()));

		Response iterable = serviceMuntant.getListAdn();

		Assertions.assertEquals(response, iterable);

	}

	@Test
	void getAdnStatusSuccess() throws Exception {
		Response response = null;
		StatiAdnDto statusOne = AdnFactory.getAnyStatiAdnDto();


		response = new Response(HttpStatus.OK.value(), "consulta exitosa", statusOne);
		when(this.serviceMuntant.getStats()).thenReturn(response);
		restMock.perform(get(URI.create("/api/v1/muntant/stats")).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(response).getBytes()).characterEncoding("utf-8")).andDo(print())//;
				.andExpect(status().isOk())//;
				.andExpect(jsonPath(RESPONSE_STATUS).value(HttpStatus.OK.value()));

		Response iterable = serviceMuntant.getStats();

		Assertions.assertEquals(response, iterable);

	}
	
	
	@Test
	void validateMutantSuccess() throws Exception {
		Response response = null;
		AdnDto adnDto = AdnFactory.getAnyAdnDtoMutant();

		response = new Response(HttpStatus.OK.value(), "consulta exitosa", MUTANT_TRUE.toString());
		when(this.serviceMuntant.validateMutant(any(AdnDto.class))).thenReturn(response);
		restMock.perform(post(URI.create("/api/v1/muntant/")).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(response).getBytes()).characterEncoding("utf-8")).andDo(print())//;
				.andExpect(status().isOk())//;
				.andExpect(jsonPath(RESPONSE_STATUS).value(HttpStatus.OK.value()));

		Response iterable = serviceMuntant.validateMutant(adnDto);

		Assertions.assertEquals(response, iterable);
		verify(this.serviceMuntant, times(1)).validateMutant(adnDto);

	}
	
	@Test
	void validateHumanSuccess() throws Exception {
		Response response = null;
		AdnDto adnDto = AdnFactory.getAnyAdnDtoHuman();

		response = new Response(HttpStatus.FORBIDDEN.value(), "consulta exitosa", MUTANT_FALSE.toString());
		when(this.serviceMuntant.validateMutant(any(AdnDto.class))).thenReturn(response);
		restMock.perform(post(URI.create("/api/v1/muntant/")).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(response).getBytes()).characterEncoding("utf-8")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath(RESPONSE_STATUS).value(HttpStatus.FORBIDDEN.value()));

		Response iterable = serviceMuntant.validateMutant(adnDto);

		Assertions.assertEquals(response, iterable);
		verify(this.serviceMuntant, times(1)).validateMutant(adnDto);

	}
}
