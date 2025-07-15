package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.model.Participanteromeria;
import com.boot.model.Propuesta;
import com.boot.model.Votopropuesta;

public interface VotoPropuestaRepository extends JpaRepository<Votopropuesta, Integer>{
	
	Votopropuesta findByParticipanteromeriaAndPropuesta(Participanteromeria participante, Propuesta propuesta);
    @Query("SELECT p FROM Votopropuesta p WHERE p.participanteromeria.year.isActive = true")
    List<Votopropuesta> findAllByActiveYear();
	  
    @Query("""
			    SELECT p.participanteromeria.usuario.nombre 
			    FROM Votopropuesta p 
			    WHERE p.propuesta.id = :propuestaId AND p.isAFavor = 1
	""")
	List<String> findNombresVotosAFavor(@Param("propuestaId") Long propuestaId);

	@Query("""
			    SELECT p.participanteromeria.usuario.nombre 
			    FROM Votopropuesta p 
			    WHERE p.propuesta.id = :propuestaId AND p.isAFavor = 0
	""")
	List<String> findNombresVotosEnContra(@Param("propuestaId") Long propuestaId);
	List<Votopropuesta> findByPropuestaId(Long propuestaId);


}
