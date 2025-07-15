package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boot.model.Propuesta;

public interface PropuestaRepository extends JpaRepository<Propuesta, Integer>{
//	List<Propuesta> findAllByYearIsActiveTrue();
	  @Query("SELECT p FROM Propuesta p " +
	           "WHERE p.participanteromeria.year.isActive = true")
	    List<Propuesta> findAllByActiveYear();
}
