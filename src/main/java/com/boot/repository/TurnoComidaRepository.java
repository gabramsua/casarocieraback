package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boot.model.Turnocomida;
import com.boot.model.Year;

public interface TurnoComidaRepository extends JpaRepository<Turnocomida, Integer>{

	Turnocomida findByNombreAndYear(String nombre, Year year);

    List<Turnocomida> findAllByYearIsActiveTrue();
    List<Turnocomida> findAllByYearIsActiveTrueAndYearCasaId(int id);
}
