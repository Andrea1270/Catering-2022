package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;


@Entity
public class Buffet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank //no spazi vuoti o bianchi
	private String nome;

	@NotBlank //no spazi vuoti o bianchi
	private String Descrizione;

	/*ASSOCIAZIONI*/
	@OneToOne
	private Chef chef;//manytoONE EAGER
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Piatto> piatti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return Descrizione;
	}

	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}	
	
	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	
//	public void addPiatto(Piatto piatto) {
//		this.getPiatti().add(piatto);
//	}

	@Override
	public String toString() {
		return "Buffet [id=" + id + ", nome=" + nome + ", Descrizione=" + Descrizione + "]";
	}
}
