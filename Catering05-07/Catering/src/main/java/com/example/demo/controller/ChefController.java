package com.example.demo.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.validator.ChefValidator;

@Controller
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefValidator chefValidator;
	
	/*CREAZIONE DI UNO CHEF DALL'ADMIN*/
	@GetMapping("/admin/chef")
	public String createChef(Model model){
		model.addAttribute("chef", new Chef());
		return "admin/chefForm.html";
	}
	
	/*EFFETTIVO INSERIMENTO DI UNO CHEF DALL'ADMIN*/
	@PostMapping("/admin/chef")
	public String addBuffet(@ModelAttribute("ListaChef") Chef chef, 
			Model model, BindingResult bindingResult) {
		
		this.chefValidator.validate(chef, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			this.chefService.inserisci(chef);
			model.addAttribute("ListaChef", this.chefService.tutti());
			return "admin/listaChef.html";
		}
		return "admin/chefForm.html";
	}
	
	/*RESTITUZIONE DI TUTTI GLI CHEF PER ADMIN*/
	@GetMapping("/admin/listaChef")
	public String getListaChef(Model model) {
		model.addAttribute("ListaChef", this.chefService.tutti());
		return "admin/listaChef.html";
	}
	
	/*RESTITUZIONE DI TUTTI I BUFFET PER USER*/
	@GetMapping("/listaChef")
	public String getListaChef2(Model model) {
		model.addAttribute("ListaChef", this.chefService.tutti());
		return "listaChef.html";
	}
	
	@GetMapping("/admin/chef/{id}")
	public String dettagliChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("Chef", this.chefService.ChefPerId(id));
		return "admin/chef.html";
	}
	
	@GetMapping("/chef/{id}")
	public String dettagliChefUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("Chef", this.chefService.ChefPerId(id));
		return "chef.html";
	}
	
	@GetMapping("/admin/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		
		Chef chef = chefService.ChefPerId(id);
		for(Buffet b : buffetService.findAllByChef(chef)) {
			b.setChef(null);
		}
		chefService.deleteChefPerId(id);
		model.addAttribute("ListaChef", this.chefService.tutti());
		return "admin/listaChef.html";
	}
	
}
