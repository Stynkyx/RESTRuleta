package com.ibm.academia.apirest.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "clientes")
public class Cliente implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellido")
	private String apellido;
	
	@Column(name ="dinero")
	private int dinero;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "cliente_ruleta",
			joinColumns = @JoinColumn(name = "cliente_id"),
			inverseJoinColumns = @JoinColumn(name = "ruleta_id")
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "clientes"})
	private List<Ruleta> ruletas;
	
	public void addRuleta(Ruleta ruleta) {
	        ruletas.add(ruleta);
	        ruleta.getClientes().add(this);
	}
	
	public void removeRuleta(Ruleta ruleta) {
        ruletas.remove(ruleta);
        ruleta.getClientes().remove(this);
    }
	
	private static final long serialVersionUID = 6079659304853308307L;

}
