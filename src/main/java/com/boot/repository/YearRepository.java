package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Year;

public interface YearRepository extends JpaRepository<Year, Integer>{

	Year findByNombre(String nombre);
	Year findByIsActiveTrue();

}
