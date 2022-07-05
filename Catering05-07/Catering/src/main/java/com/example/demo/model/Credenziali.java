package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;


@Entity
public class Credenziali {
	
	public static final String DEFAULT = "DEFAULT";
	public static final String ADMIN = "ADMIN";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank
	private String username;

	@Column(nullable = false)
	@NotBlank
	private String password;
	
	@Column(nullable = false)
	@NotBlank
	private String ruolo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Utente utente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String role) {
		this.ruolo = role;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
}
