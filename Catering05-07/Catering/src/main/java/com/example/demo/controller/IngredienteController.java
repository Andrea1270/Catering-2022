package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Ingrediente;
import com.example.demo.service.IngredienteService;
import com.example.demo.validator.IngredienteValidator;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private IngredienteValidator ingredienteValidator;

	@GetMapping("/admin/ingredienteForm")
	public String ingredienteForm(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "/admin/ingredienteForm.html"; //da fare
	}
	
	@GetMapping("/admin/ingredienti")
	public String getIngredienti(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.tutti();
		model.addAttribute("ListaIngredienti", ingredienti);
		return "/admin/ingredienti.html"; 
	}
	
	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente, 
			BindingResult bindingResult, Model model) {
		this.ingredienteValidator.validate(ingrediente, bindingResult);
		
		if (!bindingResult.hasErrors()) {	
			this.ingredienteService.save(ingrediente); // salvo un oggetto Ingrediente
			model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
			return "/admin/listaIngredienti.html";
		}
		else {
			model.addAttribute("ingrediente", ingrediente);
			// se ci sono errori si rimanda alla form di inserimento
			return "/admin/ingredienteForm.html"; 
		}
	}
	
	// richiede un singolo ingrediente tramite id
	@GetMapping("/admin/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Ingrediente ingrediente = ingredienteService.ingredientePerId(id);
		model.addAttribute("ingrediente", ingrediente);
		// ritorna la pagina con i dati dell'entità richiesta
		return "/admin/ingrediente.html"; 
	}
	
	@GetMapping("/ingrediente/{id}")
	public String getIngredienteUser(@PathVariable("id")Long id, Model model) {
		model.addAttribute("ingrediente", ingredienteService.ingredientePerId(id));
		// ritorna la pagina con i dati dell'entità richiesta
		return "/ingrediente.html"; 
	}
	
	
}
