package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Piatto;
import com.example.demo.repository.BuffetRepository;

@Service
public class BuffetService {
	
	@Autowired
	private BuffetRepository buffetRepository; 
	
	@Transactional
	public Buffet inserisci(Buffet buffet) {
		return buffetRepository.save(buffet); 
	}
	
//	@Transactional
//	public Buffet update(Buffet buffetVecchio) {
//		
//    	List<Piatto> listap = new ArrayList<>();
//    	
//    	listap = buffetVecchio.getPiatti();   	
//    	Chef chef = buffetVecchio.getChef();   	
//    	Buffet nuovoBuffet = new Buffet();    	
//    	nuovoBuffet.setChef(chef);
//    	nuovoBuffet.setPiatti(listap);
//    	
//    	buffetRepository.delete(buffetVecchio);
//    	buffetRepository.save(nuovoBuffet);
//    	
//    	return nuovoBuffet;
//	}

	@Transactional
	public List<Buffet> tutti() {
		return (List<Buffet>) buffetRepository.findAll();
	}

	@Transactional
	public Buffet BuffetPerId(Long id) {
		Optional<Buffet> optional = buffetRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Buffet Buffet) {
		List<Buffet> prodotti = this.buffetRepository.findByNome(Buffet.getNome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteBuffetPerId(Long id) {
		Optional<Buffet> optional = buffetRepository.findById(id);
		if (optional.isPresent())
			buffetRepository.deleteById(id);
	}
	
	@Transactional
	public List<Buffet> findAllByChef(Chef chef) {
		List<Buffet> buffets = new ArrayList<Buffet>();
		for(Buffet b : buffetRepository.findAllByChef(chef)) {
			buffets.add(b);
		}
		return buffets;
	}
	
	@Transactional
	public List<Buffet> findAllByPiatti(Piatto piatto) {
		List<Buffet> buffets = new ArrayList<Buffet>();
		for(Buffet b : buffetRepository.findAllByPiatti(piatto)) {
			buffets.add(b);
		}
		return buffets;
	}

}
