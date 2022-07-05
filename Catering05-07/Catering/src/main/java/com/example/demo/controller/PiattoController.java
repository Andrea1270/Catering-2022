package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Buffet;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.PiattoValidator;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private BuffetService buffetService;

	@Autowired
	private PiattoValidator piattoValidator;
	
	/*inserimento di un nuovo piatto dalla home dell'admin*/
	@GetMapping("/admin/piatto")
	public String piattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		return "admin/piattoForm.html";
	}
	
	@GetMapping("/admin/listaPiatti")
	public String listaDeiPiatti(Model model) {
		model.addAttribute("ListaPiatti", this.piattoService.tutti());
		return "admin/listaPiatti.html";
	}
	
	@PostMapping("/admin/piatto")
	public String addPiatto(@ModelAttribute("piatto") Piatto piatto, 
			BindingResult bindingResult, Model model) {
		
		this.piattoValidator.validate(piatto, bindingResult);

		if (!bindingResult.hasErrors()) {
			this.piattoService.save(piatto); // salvo un oggetto Piatto
			model.addAttribute("ListaPiatti", this.piattoService.tutti());;
			return "admin/listaPiatti.html"; 
		} 
		else {
			// se ci sono errori si rimanda alla form di inserimento
			return "admin/piattoForm.html"; 
		}
	}
	
    /*CANCELLAZIONE DA PARTE DELL'ADMIN DI UN BUFFET PER ID*/
    @GetMapping("/admin/deletePiattoDaLista/{id}")
    public String deletePiattoDaLista(@PathVariable("id") Long id, Model model) {
    	
    	Piatto piatto = piattoService.PiattoPerId(id);
    	
    	for(Buffet b : buffetService.findAllByPiatti(piatto)) {
			b.getPiatti().remove(piatto);
		}
    	
    	piattoService.deletePiattoPerId(id);
    	model.addAttribute("ListaPiatti", this.piattoService.tutti());;
    	return "admin/listaPiatti.html"; 
    }
    
    /*RESTITUZIONE DELLE INFORMAZIONI DEL PIATTO PASSATO PER ID*/
    @GetMapping("/admin/piatto/{id}")
    public String dettagliPiatto(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("piatto", this.piattoService.PiattoPerId(id));
    	model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
    	model.addAttribute("ingredientiPiatto", this.piattoService.ingredientiPiatto(id)); 
    	return "admin/piatto.html";
    }
    
    @GetMapping("/piatto/{id}")//TODO
    public String dettagliPiattoUser(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("piatto", this.piattoService.PiattoPerId(id));
    	model.addAttribute("ingredientiPiatto", this.piattoService.ingredientiPiatto(id)); 
    	return "piatto.html";
    }
    
    @GetMapping("/admin/piatto/{piatto_id}/{ingrediente_id}")
    public String addIngredienteAlPiatto(@PathVariable("piatto_id") Long piatto_id,@PathVariable("ingrediente_id") Long ingrediente_id, Model model) {
    	
    	Piatto piatto = piattoService.PiattoPerId(piatto_id);
    	Ingrediente ingrediente = ingredienteService.ingredientePerId(ingrediente_id);
    	
    	piatto.getIngredienti().add(ingrediente);
//    	ingredienteService.save(ingrediente);
    	
    	model.addAttribute("piatto", this.piattoService.PiattoPerId(piatto_id));
    	model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
    	model.addAttribute("ingredientiPiatto", this.piattoService.ingredientiPiatto(piatto_id)); 
    	
    	return "admin/piatto.html";
    }
    
    @GetMapping("/admin/deleteIngredientePiatto/{piatto_id}/{ingrediente_id}")
    public String deleteIngredienteDaPiatto(@PathVariable("piatto_id") Long piatto_id,@PathVariable("ingrediente_id") Long ingrediente_id, Model model) {
    	
    	Piatto piatto = piattoService.PiattoPerId(piatto_id);
    	Ingrediente ingrediente = ingredienteService.ingredientePerId(ingrediente_id);
    	
    	piatto.getIngredienti().remove(ingrediente);
    	
    	model.addAttribute("piatto", this.piattoService.PiattoPerId(piatto_id));
    	model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
    	model.addAttribute("ingredientiPiatto", this.piattoService.ingredientiPiatto(piatto_id));
    	
    	return "admin/piatto.html";
    }
}
