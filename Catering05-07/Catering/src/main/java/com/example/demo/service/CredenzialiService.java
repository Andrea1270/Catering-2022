package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Credenziali;
import com.example.demo.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredenzialiRepository credenzialiRepository;

	@Transactional
	public Credenziali salvaCredenziali(Credenziali credenziali) {
		credenziali.setRuolo(Credenziali.ADMIN);
//		credenziali.setRuolo(Credenziali.DEFAULT);
    	credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
		return this.credenzialiRepository.save(credenziali);
	}
	
	@Transactional
	public Credenziali getCredenziali(Long id) {
		Optional<Credenziali> result = this.credenzialiRepository.findById(id);//optional->Contenitore di valori null oppure no
		return result.orElse(null);//orElse->se presente ritorna il valore di result altrimento il parametro
	}

	@Transactional
	public Credenziali getCredenziali(String username) {
		Optional<Credenziali> result = this.credenzialiRepository.findByUsername(username);
		return result.orElse(null);
	}
}
