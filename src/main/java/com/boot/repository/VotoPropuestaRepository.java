package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Participanteromeria;
import com.boot.model.Propuesta;
import com.boot.model.Votopropuesta;

public interface VotoPropuestaRepository extends JpaRepository<Votopropuesta, Integer>{
	
	Votopropuesta findByParticipanteromeriaAndPropuesta(Participanteromeria participante, Propuesta propuesta);

}
