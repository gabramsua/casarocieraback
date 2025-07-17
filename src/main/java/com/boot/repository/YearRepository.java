package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.model.Casa;
import com.boot.model.Year;
import com.boot.pojo.EventoActivo;

public interface YearRepository extends JpaRepository<Year, Integer>{

	Year findByNombre(String nombre);
	Year findByIsActiveTrueAndCasaId(int idCasa);
	Year findByNombreAndCasa(String nombre, Casa casa);
	
	@Query("SELECT y, c.nombre FROM Year y JOIN y.casa c WHERE y.isActive = true AND c.id = :idCasa")
	EventoActivo findByIsActiveTrueAndCasaIdWithCasaNombre(@Param("idCasa") int idCasa);
}
