package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.model.Participanteromeria;
import com.boot.model.Usuario;
import com.boot.model.Year;

public interface ParticipanteRomeriaRepository extends JpaRepository<Participanteromeria, Integer>{

	Participanteromeria findByUsuarioAndYear(Usuario usuario, Year year);
	
	@Query("SELECT p FROM Participanteromeria p WHERE p.year.isActive = true")
	List<Participanteromeria> findAllByYearActive();
	
	@Query("SELECT pr FROM Participanteromeria pr JOIN pr.year y JOIN pr.usuario u WHERE y.isActive = true AND u.casa.id = :idCasa AND u.casa.id = y.casa.id")
    List<Participanteromeria> findActiveParticipantsByCasaId(@Param("idCasa") int idCasa);

	

    List<Participanteromeria> findAllByYearIsActiveTrueAndYearCasaId(int idCasa);

}
