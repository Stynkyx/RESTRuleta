package com.ibm.academia.apirest.models.services;

import java.util.List;
import java.util.Optional;

import com.ibm.academia.apirest.models.entities.Ruleta;

public interface IRuletaService extends GenericoDAO<Ruleta>
{
	public List<Ruleta> buscarTodos();
	public Optional<Ruleta> buscarPorId(Integer ruletaId);
	//1 crear ruleta y mostrar id
	//2 abrir ruleta 
	public Ruleta abrirRuleta(Ruleta ruletaEncontrada, Ruleta ruleta);
	//3 apuesta numero
	public Ruleta apuestaNumero(Integer apuesta);
	//4 cierre ruleta
	public Ruleta cerrarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta);
	public Ruleta apuesta(Ruleta ruletaEncontrada, Ruleta ruleta);
	public boolean estaAbierta(Integer ruletaId);

}