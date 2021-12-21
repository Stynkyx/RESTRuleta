package com.ibm.academia.apirest.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.models.repositories.RuletaRepository;

@Service
public class RuletaServiceImpl extends GenericoDAOImpl<Ruleta, RuletaRepository> implements IRuletaService 
{
	@Autowired
	public RuletaServiceImpl(RuletaRepository repository) {
		super(repository);
	}

		
	@Override
	@Transactional(readOnly = true)
	public List<Ruleta> buscarTodos() 
	{
		return (List<Ruleta>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Ruleta> buscarPorId(Integer ruletaId) 
	{
		return repository.findById(ruletaId);
	}

	
	@Override
	public Ruleta abrirRuleta(Ruleta ruletaEncontrada, Ruleta ruleta) {
		Ruleta ruletaActualizada = null;
		ruletaEncontrada.setEstadoRuleta(true);
		ruletaActualizada = repository.save(ruletaEncontrada);
		return ruletaActualizada;
	}

	@Override
	public Ruleta apuestaNumero(Integer apuesta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ruleta cerrarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta) {
		Ruleta ruletaActualizada = null;
		ruletaEncontrada.setEstadoRuleta(false);
		ruletaActualizada = repository.save(ruletaEncontrada);
		return ruletaActualizada;
	}


	@Override
	public boolean estaAbierta(Integer ruletaId) {
		Optional<Ruleta> ruletaEncontrada= repository.findById(ruletaId);
		boolean estado=true;
		if(!ruletaEncontrada.get().isEstadoRuleta())
			estado=false;
		return estado;
	}


	@Override
	public Ruleta apuesta(Ruleta ruletaEncontrada, Ruleta ruleta) {
		Ruleta ruletaActualizada = null;
		ruletaEncontrada.setNumeroApuesta(ruleta.getNumeroApuesta());
		Integer sorteo;
		sorteo = (int)(Math.random()*5+1);
		if(sorteo==ruleta.getNumeroApuesta())
			ruletaEncontrada.setGano(true);
		else
			ruletaEncontrada.setGano(false);
		ruletaActualizada = repository.save(ruletaEncontrada);
		return ruletaActualizada;
	}

}