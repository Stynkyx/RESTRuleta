package com.ibm.academia.apirest.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "ruletas")
public class Ruleta implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "estato_ruleta")
	private boolean estadoRuleta=false;
	
	@Column(name = "dinero_inicio")
	private double dineroInicio;
	
	@Column(name = "dinero_final")
	private double dineroFinal;
	
	@Column(name ="numero_apuesta")
	private int numeroApuesta;
	
	@Column(name = "gano")
	private boolean gano=false;
	
	@ManyToMany(mappedBy = "ruletas", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"ruletas"})
	private List<Cliente> clientes;
	
	private static final long serialVersionUID = -4262301090755554884L;

}
