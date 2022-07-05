package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;


@Entity
public class Piatto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank //no spazi vuoti o bianchi
	private String nome;

	@NotBlank //no spazi vuoti o bianchi
	private String descrizione;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ingrediente> ingredienti;

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
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	@Override
	public String toString() {
		return "Piatto [id=" + id + ", nome=" + nome + ", Descrizione=" + descrizione + "]";
	}
	
}
