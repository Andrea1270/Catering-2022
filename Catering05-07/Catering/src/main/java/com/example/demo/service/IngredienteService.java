package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ingrediente;
import com.example.demo.repository.IngredienteRepository;


@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Transactional
	public void save(Ingrediente ingrediente) { 
		ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) { 
		ingredienteRepository.delete(ingrediente);
	}
	
	@Transactional
	public Ingrediente ingredientePerId(Long id) {
		Optional<Ingrediente> optional = ingredienteRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public List<Ingrediente> tutti() {
		return (List<Ingrediente>) ingredienteRepository.findAll();
	}
	
	@Transactional
	public boolean alreadyExists(Ingrediente ingrediente) {
		List<Ingrediente> prodotti = this.ingredienteRepository.findByNome(ingrediente.getNome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteingredientePerId(Long id) {
		Optional<Ingrediente> optional = ingredienteRepository.findById(id);
		if (optional.isPresent())
			ingredienteRepository.deleteById(id);
	}
	

}
