package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Participanteromeria;
import com.boot.model.Usuario;
import com.boot.model.Year;

public interface ParticipanteRomeriaRepository extends JpaRepository<Participanteromeria, Integer>{

	Participanteromeria findByUsuarioAndYear(Usuario usuario, Year year);

}
