package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.service.PiattoService;
import com.example.demo.validator.BuffetValidator;



@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService chefService; 
	
	@Autowired
	private PiattoService piattoService;

	@Autowired
	private BuffetValidator buffetValidator;

	/*RESTITUZIONE DI UN BUFFET PER ID*/
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.BuffetPerId(id));
		return "buffet.html";
	}

	/*RESTITUZIONE DI TUTTI I BUFFET PER USER*/
	@GetMapping("/ListaBuffet")
	public String getListaBuffet(Model model) {
		model.addAttribute("ListaBuffet", this.buffetService.tutti());
		return "listaBuffet.html";
	}
	
	/*RESTITUZIONE DI TUTTI I BUFFET PER ADMIN*/
	@GetMapping("/admin/ListaBuffet")
	public String getListaBuffet2(Model model) {
		model.addAttribute("ListaBuffet", this.buffetService.tutti());
		return "/admin/listaBuffet.html";
	}

	/*CREAZIONE DI UN BUFFET DALL'ADMIN*/
	@GetMapping("/admin/buffet")
	public String createBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "admin/buffetForm.html"; //form per la creazione di un buffet
	}
	
	/*EFFETTIVO INSERIMENTO DI UN BUFFET DALL'ADMIN*/
	@PostMapping("/admin/buffet")
	public String addBuffet(@ModelAttribute("ListaBuffet") Buffet buffet, 
			Model model, BindingResult bindingResult) {
		
		this.buffetValidator.validate(buffet, bindingResult);
		
		/* se non ho riscontrato inserisci il prodotto il lista e restituiscimi tutta la lista*/
		if (!bindingResult.hasErrors()) {
			this.buffetService.inserisci(buffet);
			model.addAttribute("ListaBuffet", this.buffetService.tutti());
			return "admin/listaBuffet.html";
		}
		return "admin/buffetForm.html";
	}
	
	/*RICERCA DI UN BUFFET PER ID PER USER*/
    @GetMapping("/ListaBuffet/{id}")
    public String getBuffetId(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("buffet", this.buffetService.BuffetPerId(id));
        model.addAttribute("ListaPiattiBuffet", this.buffetService.BuffetPerId(id).getPiatti());
    	return "buffet.html";
    }
    
    /*CANCELLAZIONE DA PARTE DELL'ADMIN DI UN BUFFET PER ID*/
    @GetMapping("/admin/deleteBuffet/{id}")
    public String deleteBuffet(@PathVariable("id") Long id, Model model) {
    	buffetService.deleteBuffetPerId(id);
    	model.addAttribute("ListaBuffet", this.buffetService.tutti());
    	return "admin/listaBuffet.html";
    }
    
//    @GetMapping("/admin/editBuffet/{id}")
//    public String editBuffet(@PathVariable("id") Long id, Model model) {  	
//    	
//    	Buffet nuovoBuffet = buffetService.update(buffetService.BuffetPerId(id));
//    	
//    	model.addAttribute("buffet", nuovoBuffet);
//    		
//    	return "admin/editBuffetForm.html";
//    }
    
    /*RICERCA DI UN BUFFET PER ID PER ADMIN*/
    /* INSERISCO NELLA PAGINA DEL BUFFET SINGOLO TUTTE LE INFO NECESSARIE*/
    @GetMapping("/admin/ListaBuffet/{id}")
    public String getBuffetId2(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("buffet", this.buffetService.BuffetPerId(id));
    	model.addAttribute("ListaChef", this.chefService.tutti());
    	model.addAttribute("ListaPiatti", this.piattoService.tutti());
        model.addAttribute("ListaPiattiBuffet", this.buffetService.BuffetPerId(id).getPiatti());
    	return "admin/buffet.html";
    }
    
    @GetMapping("/admin/buffet/{buffet_id}/{chef_id}")
    public String addChefDaButton(@PathVariable("chef_id") Long chef_id, @PathVariable("buffet_id") Long buffet_id, Model model) {

        Chef chef = chefService.ChefPerId(chef_id);
        Buffet buffet = buffetService.BuffetPerId(buffet_id);

        buffet.setChef(chef);
        chefService.inserisci(chef);

        model.addAttribute("buffet", buffet);
        model.addAttribute("ListaChef", chefService.tutti());
        model.addAttribute("ListaPiatti", piattoService.tutti());
        model.addAttribute("ListaPiattiBuffet", buffet.getPiatti());

        return "admin/buffet.html";
    }
    
    @GetMapping("/admin/buffetPiatto/{buffet_id}/{piatto_id}")
    public String addPiattoDaButton(@PathVariable("buffet_id") Long buffet_id, @PathVariable("piatto_id") Long piatto_id, Model model) {

        Piatto piatto = piattoService.PiattoPerId(piatto_id);
        Buffet buffet = buffetService.BuffetPerId(buffet_id);
        
        if(!buffet.getPiatti().contains(piatto)) {
        	buffet.getPiatti().add(piatto);
//            piattoService.save(piatto);
        }

        model.addAttribute("buffet", buffet);
        model.addAttribute("ListaPiatti", piattoService.tutti());
        model.addAttribute("ListaChef", chefService.tutti());
        model.addAttribute("ListaPiattiBuffet", buffet.getPiatti());

        return "admin/buffet.html";
    }
    
    /*CANCELLAZIONE DA PARTE DELL'ADMIN DI UN BUFFET PER ID*/
    @GetMapping("/admin/deletePiatto/{buffet_id}/{piatto_id}")
    public String deletePiatto(@PathVariable("buffet_id") Long buffet_id, @PathVariable("piatto_id") Long piatto_id, Model model) {
    	
        Piatto piatto = piattoService.PiattoPerId(piatto_id);
        Buffet buffet = buffetService.BuffetPerId(buffet_id);
        
        buffet.getPiatti().remove(piatto);
        
        model.addAttribute("buffet", buffet);
        model.addAttribute("ListaPiatti", piattoService.tutti());
        model.addAttribute("ListaChef", chefService.tutti());
        model.addAttribute("ListaPiattiBuffet", buffet.getPiatti());
    	
    	return "admin/buffet.html";
    }
    
    

}
