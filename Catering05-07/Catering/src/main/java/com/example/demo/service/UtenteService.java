package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Utente;
import com.example.demo.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Transactional
	public void salvaUtente(Utente utente) {
		utenteRepository.save(utente);
	}

}
