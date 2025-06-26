package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Turnocomida;
import com.boot.model.Year;

public interface TurnoComidaRepository extends JpaRepository<Turnocomida, Integer>{

	Turnocomida findByNombreAndYear(String nombre, Year year);

}
