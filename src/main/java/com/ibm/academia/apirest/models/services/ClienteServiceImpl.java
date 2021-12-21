package com.ibm.academia.apirest.models.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.models.entities.Cliente;
import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.models.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl extends GenericoDAOImpl<Cliente, ClienteRepository> implements IClienteService{

	public ClienteServiceImpl(ClienteRepository repository) {
		super(repository);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() 
	{
		return (List<Cliente>) repository.findAll();
	}

	@Override
	public Cliente asociarRuletaCliente(Cliente cliente, Ruleta ruleta) {
		cliente.addRuleta(ruleta);
		return repository.save(cliente);
	}
}
