package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Aportacionparticipante;
import com.boot.model.Participanteromeria;

public interface AportacionparticipanteRepository extends JpaRepository<Aportacionparticipante, Integer>{

	List<Aportacionparticipante> findByParticipanteRomeria(Participanteromeria participanteromeria);

}
