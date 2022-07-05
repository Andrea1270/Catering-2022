package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Credenziali;
import com.example.demo.model.Utente;
import com.example.demo.service.CredenzialiService;
import com.example.demo.validator.CredenzialiValidator;
import com.example.demo.validator.UtenteValidator;

@Controller
public class CredenzialiController {

	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	private UtenteValidator utenteValidator;

	@Autowired
	private CredenzialiValidator credenzialiValidator;
	
	/* ----------------------------------PATH DELLE REGISTRAZIONI---------------------------------*/
	
	@GetMapping("/register") //se leggo nell'html /register vuoldire che utilizzerà questo metodo del controller
	public String showResisterForm(Model model) {
		/* creo le cose necessarie al modello per poter lavorare con l'html*/
		model.addAttribute("Utente", new Utente());
		model.addAttribute("Credenziali", new Credenziali());
		return "registerForm.html";
	}
	
	@GetMapping("/failure")
	public String showFailurePage(Model mode) {
		return "failure.html";
	}
	
	@PostMapping("/RegistrazioneUtente")
	public String addUtente(@ModelAttribute("Utente")Utente utente,
			BindingResult utenteBindingResult,
			@ModelAttribute("Credenziali") Credenziali credenziali,
			BindingResult credenzialiBindingResult,Model model) {

		// Controlliamo se le l'utente e le credenziali rispettano le regole del validator di entrambi
		this.utenteValidator.validate(utente, utenteBindingResult);
		this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);

		// se entrambi non hanno errori
		if(!utenteBindingResult.hasErrors() && ! credenzialiBindingResult.hasErrors()) {
			credenziali.setUtente(utente);
			credenzialiService.salvaCredenziali(credenziali);
			return "registrazioneEffettuata.html";//da completare con un bottone che redirecta alla home
		}
		return "registerForm.html";
	}	
	
	/* ----------------------------------PATH DEL LOGIN---------------------------------*/
	
	@GetMapping("/login") //il value è il valore che inserisco nell'href dell'html
	public String showLoginForm(Model model) {
		return "loginForm.html";
	}

	/* CONTROLLO SE SONO UN ADMIN, SE LO SONO VAMMI NELLA HOME COME ADMIN ALTRIMENTI COME USER*/
	@GetMapping("/default") 
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
    	if (credenziali.getRuolo().equals(Credenziali.ADMIN)) {
            return "admin/home.html";
        }
        return "home.html";
    }
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "index.html";
	}
	

}
