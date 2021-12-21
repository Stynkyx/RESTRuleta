package com.ibm.academia.apirest.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Cliente;
import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.models.services.IClienteService;
import com.ibm.academia.apirest.models.services.IRuletaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cliente")
@Api(value = "Métodos relacionados con los clientes", tags = "Acciones sobre clientes")
public class ClienteController {
	Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IRuletaService ruletaService;
	/**
	 * EndPoint para mostrar una lista de objetos cliente
	 * @param BadRequestException En caso de que no se encuentre ningun elemento en la base de datos 
	 * @return Una lista de clientes
	 * @author SACM - 20-12-21
	 */
	@ApiOperation(value = "Consulta todas las ruletas")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ejecutado exitosamente")
	})
	@GetMapping("/listar")
	public ResponseEntity<?> listarClientes()
	{
		List<Cliente> clientes = clienteService.buscarTodos();
		
		if(clientes.isEmpty())
			throw new NotFoundException("No existen clientes en la base de datos.");
		
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}
	
	@PutMapping("/clienteId/{clienteId}/ruletaId/{ruletaId}")
	public ResponseEntity<?> asignarApuestaRuleta(@PathVariable Integer clienteId, @PathVariable Integer ruletaId,@RequestBody Ruleta ruleta)
	{
		Optional<Cliente> oCliente = clienteService.buscarPorId(clienteId);
        if(!oCliente.isPresent()) 
            throw new NotFoundException(String.format("Cliente con id %d no existe", clienteId));
        
        Optional<Ruleta> oRuleta = ruletaService.buscarPorId(ruletaId);
        if(!oRuleta.isPresent())
            throw new NotFoundException(String.format("Ruleta con id %d no existe", ruletaId));
        
        if(!ruletaService.estaAbierta(ruletaId))
			throw new NotFoundException(String.format("La ruleta con ID: %d no esta abierta", ruletaId));
        
        ruletaService.apuesta(oRuleta.get(), ruleta);
        
        Cliente cliente = clienteService.asociarRuletaCliente(oCliente.get(), oRuleta.get());
        
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
}
