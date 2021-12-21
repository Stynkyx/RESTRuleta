package com.ibm.academia.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.mapper.RuletaMapper;
import com.ibm.academia.apirest.models.dto.RuletaDTO;
import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.models.services.IRuletaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ruleta")
@Api(value = "Métodos relacionados con las ruletas", tags = "Acciones sobre ruletas")
public class RuletaController 
{
	Logger logger = LoggerFactory.getLogger(RuletaController.class);
	
	@Autowired
	private IRuletaService ruletaService;
	
	/**
	 * Endpoint para crear un objeto ruleta
	 * @param ruleta Objeto ruleta con la informacion para crear
	 * @param result En caso de que un campo no se haya llenado correctamente
	 * @return Retorna el objeto tarjeta creado junto con código httpstatus 201
	 * @author SACM - 20-12-21
	 */
	@PostMapping
	public ResponseEntity<?> guardarRuleta(@Valid @RequestBody Ruleta ruleta, BindingResult result)
	{
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors())
		{
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista Errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		Ruleta ruletaGuardada = ruletaService.guardar(ruleta);
		String respuesta = String.format("Ruleta con id %s creada exitosamente",ruletaGuardada.getId().toString());
		return new ResponseEntity<String>(respuesta, HttpStatus.CREATED);
	}
	
	/**
	 * EndPoint para mostrar una lista de objetos ruleta
	 * @param BadRequestException En caso de que no se encuentre ningun elemento en la base de datos 
	 * @return Una lista de ruletas
	 * @author SACM - 20-12-21
	 */
	@ApiOperation(value = "Consulta todas las ruletas")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ejecutado exitosamente")
	})
	@GetMapping("/listar")
	public ResponseEntity<?> listarRuletas()
	{
		List<Ruleta> ruletas = ruletaService.buscarTodos();
		
		if(ruletas.isEmpty())
			throw new NotFoundException("No existen ruletas en la base de datos.");
		
		return new ResponseEntity<List<Ruleta>>(ruletas, HttpStatus.OK);
	}
	/**
	 * EndPoint para mostrar una lista de objetos tarjeta
	 * @param BadRequestException En caso de que no se encuentre ningun elemento en la base de datos 
	 * @return Una lista de tarjetas con formato especial para mostrar si esta abierta o cerrada
	 * @author SACM - 09-12-21
	 */
	@GetMapping("/listar/dto")
	public ResponseEntity<?> obtenerCarrerasDTO()
	{
		List<Ruleta> ruletas = (List<Ruleta>) ruletaService.buscarTodos();
		
		if(ruletas.isEmpty())
			throw new NotFoundException("No existen ruletas en la base de datos.");
		
		List<RuletaDTO> listaRuletas = ruletas
				.stream()
				.map(RuletaMapper::mapRuleta)
				.collect(Collectors.toList());
		return new ResponseEntity<List<RuletaDTO>>(listaRuletas, HttpStatus.OK);
	}
	
	/**
	 * EndPoint para actualizar un objeto de tipo ruleta a estado abierta
	 * @param ruletaId Parametro para buscar ruleta
	 * @param ruleta Objeto con la informacion a actualizar
	 * @return Retorna un objeto de tipo String de exitoso junto con código httpstatus 200 
	 * @author SACM - 20-12-21
	 */
	@PutMapping("/abrir-ruletaId/{ruletaId}")
	public ResponseEntity<?> abrirRuleta(@PathVariable Integer ruletaId, @RequestBody Ruleta ruleta)
	{
		Optional<Ruleta> oRuleta = ruletaService.buscarPorId(ruletaId);
		
		if(!oRuleta.isPresent())
			throw new NotFoundException(String.format("La ruleta con ID: %d no existe", ruletaId));
		
		ruletaService.abrirRuleta(oRuleta.get(), ruleta); 
		String respuesta = String.format("Ruleta con id %s a sido abierta",ruletaId);
		
		return new ResponseEntity<String>(respuesta, HttpStatus.OK); 
	}
	/**
	 * EndPoint para actualizar un objeto de tipo ruleta a estado cerrado
	 * @param ruletaId Parametro para buscar ruleta
	 * @param ruleta Objeto con la informacion a actualizar
	 * @return Retorna un objeto de tipo String de exitoso junto con código httpstatus 200 
	 * @author SACM - 20-12-21
	 */
	@PutMapping("/cerrar-ruletaId/{ruletaId}")
	public ResponseEntity<?> cerrarRuleta(@PathVariable Integer ruletaId, @RequestBody Ruleta ruleta)
	{
		Optional<Ruleta> oRuleta = ruletaService.buscarPorId(ruletaId);
		
		if(!oRuleta.isPresent())
			throw new NotFoundException(String.format("La ruleta con ID: %d no existe", ruletaId));
		
		ruletaService.cerrarRuleta(oRuleta.get(), ruleta);
		
		String respuesta = String.format("Ruleta con id %s a sido cerrada",ruletaId);
		
		return new ResponseEntity<String>(respuesta, HttpStatus.OK); 
	}
		
}