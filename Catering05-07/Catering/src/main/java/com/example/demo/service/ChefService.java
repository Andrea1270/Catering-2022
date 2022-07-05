package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Chef;
import com.example.demo.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public Chef inserisci(Chef Chef) {
		return chefRepository.save(Chef); 
	}

	@Transactional
	public List<Chef> tutti() {
		return (List<Chef>) chefRepository.findAll();
	}

	@Transactional
	public Chef ChefPerId(Long id) {
		Optional<Chef> optional = chefRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Chef Chef) {
		List<Chef> prodotti = this.chefRepository.findByNome(Chef.getNome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteChefPerId(Long id) {
		Optional<Chef> optional = chefRepository.findById(id);
		if (optional.isPresent())
			chefRepository.deleteById(id);
	}

}
