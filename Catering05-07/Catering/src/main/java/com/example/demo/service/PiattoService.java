package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.repository.PiattoRepository;


@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) { 
		piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) { 
		piattoRepository.delete(piatto);
	}
	
	@Transactional
	public Piatto PiattoPerId(Long id) {
		Optional<Piatto> optional = piattoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public List<Piatto> tutti() {
		return (List<Piatto>) piattoRepository.findAll();
	}
	
	@Transactional
	public boolean alreadyExists(Piatto piatto) {
		List<Piatto> prodotti = this.piattoRepository.findByNome(piatto.getNome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deletePiattoPerId(Long id) {
		Optional<Piatto> optional = piattoRepository.findById(id);
		if (optional.isPresent())
			piattoRepository.deleteById(id);
	}
	
	@Transactional
	public List<Ingrediente> ingredientiPiatto(Long id){
		List<Ingrediente> listaIngr = this.PiattoPerId(id).getIngredienti();
		return listaIngr;
	}
	

}
