package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Casa;
import com.boot.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	Categoria findByNombre(String nombre);

	Categoria findByNombreAndCasa(String nombre, Casa casa);

	List<Categoria> findAllByCasaId(int id);
}
