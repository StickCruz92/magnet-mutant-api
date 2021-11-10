/**
 * 
 */
package com.mutant.api.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.service.ServiceMuntant;
import com.mutant.api.util.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author stick.stivenson
 *
 */
@RestController
@RequestMapping("/api/v1/muntant/")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET })
@Api("Api para el proceso de los mutantes que necesita Magneto")

public class MutantRest {

	private ServiceMuntant serviceMuntant;

	public MutantRest(ServiceMuntant serviceMuntant) {
		this.serviceMuntant = serviceMuntant;
	}

	@ApiOperation(value = "Permite a magneto validar si eres un mutante para su combate contra los x-men", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se  realiza el analisis de ADN de manera exitosa", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Los datos recibidos no cumplen con la obligatoriedad o formatos esperados", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Ocurre un error dentro del servidor, el cual se puede generar por problemas en el acceso a recursos, consultas de elementos no existentes o errores inesperados") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response isMutant(
			@ApiParam(value = "Objeto en formato JSON con el DnaDTO", name = "dna", required = true, type = "AdnDto") @RequestBody AdnDto dna) {
		return this.serviceMuntant.validateMutant(dna);
	}

	@ApiOperation(value = "Permite a magneto consultar todos ADN que a analizado hasta ahora", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La creacion del usuario se realiza de manera exitosa", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Ocurre un error dentro del servidor, el cual se puede generar por problemas en el acceso a recursos, consultas de elementos no existentes o errores inesperados") })
	@GetMapping
	public Response getListAdn() {
		return this.serviceMuntant.getListAdn();
	}

	@ApiOperation(value = "Permite a magneto consultar sus estadisticas sobre los mutantes para su combate contra los x-men", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La consulta se realiza de manera exitosa", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Ocurre un error dentro del servidor, el cual se puede generar por problemas en el acceso a recursos, consultas de elementos no existentes o errores inesperados") })
	@GetMapping("/stats")
	public Response getStats() {
		return this.serviceMuntant.getStats();
	}

}
