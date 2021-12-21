package com.ibm.academia.apirest.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Cliente;

@Repository("repositorioClientes")
public interface ClienteRepository extends CrudRepository<Cliente, Integer>{

}
