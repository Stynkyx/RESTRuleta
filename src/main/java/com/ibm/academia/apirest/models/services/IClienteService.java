package com.ibm.academia.apirest.models.services;

import java.util.List;

import com.ibm.academia.apirest.models.entities.Cliente;
import com.ibm.academia.apirest.models.entities.Ruleta;

public interface IClienteService extends GenericoDAO<Cliente>{
	
	public List<Cliente> buscarTodos();
	public Cliente asociarRuletaCliente(Cliente cliente,Ruleta ruleta);
}
