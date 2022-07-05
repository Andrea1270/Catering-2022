package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Ingrediente;

public interface IngredienteRepository  extends CrudRepository<Ingrediente, Long>{

	List<Ingrediente> findByNome(String nome);

}
