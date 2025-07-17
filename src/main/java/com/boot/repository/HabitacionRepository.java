package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer>{
	
	Habitacion findByNombre(String nombre);

	List<Habitacion> findAllByCasaId(int id);


}
