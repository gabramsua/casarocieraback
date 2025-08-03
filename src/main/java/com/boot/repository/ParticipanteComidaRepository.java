package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Participantecomida;
import com.boot.model.Participanteromeria;
import com.boot.model.Turnocomida;

public interface ParticipanteComidaRepository extends JpaRepository<Participantecomida, Integer>{
	Participantecomida findByParticipanteromeriaAndTurnocomida(Participanteromeria participanteromeria, Turnocomida t);

	List<Participantecomida> findAllByParticipanteromeriaYearIsActiveTrueAndParticipanteromeriaYearCasaId(int id);

}
