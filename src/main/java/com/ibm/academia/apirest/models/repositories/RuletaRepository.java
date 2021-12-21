package com.ibm.academia.apirest.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Ruleta;

@Repository("repositorioRuletas")
public interface RuletaRepository extends CrudRepository<Ruleta, Integer>
{

}