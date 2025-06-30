package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boot.model.Participanteromeria;
import com.boot.model.Usuario;
import com.boot.model.Year;

public interface ParticipanteRomeriaRepository extends JpaRepository<Participanteromeria, Integer>{

	Participanteromeria findByUsuarioAndYear(Usuario usuario, Year year);
	
	@Query("SELECT p FROM Participanteromeria p WHERE p.year.isActive = true")
	List<Participanteromeria> findAllByYearActive();

}
