package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	Categoria findByNombre(String nombre);
}
