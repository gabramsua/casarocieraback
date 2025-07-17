package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.DTO.PropuestasListDTO;
import com.boot.model.Votopropuesta;

public interface VotoPropuestaRepository extends JpaRepository<Votopropuesta, Integer>{

	///////////////////////////////// TO BE DELETED
	/**
    * Recupera una lista de PropuestasListDTO con el ID de la propuesta
    * y el sumatorio de votos a favor y en contra,
    * filtrando por un año activo y una casa específica.
    * Incluye propuestas que no tienen votos (con 0 en ambos sumatorios).
    *
    * @param idCasa El ID de la Casa para filtrar las propuestas.
    * @return Una lista de PropuestasListDTO.
    */
   @Query("SELECT NEW com.boot.DTO.PropuestasListDTO(" + // <-- CLAVE: Apunta a tu DTO
          "    p.id, " +
		  "    pr.usuario.nombre, " +
          "    p.fecha " + 
//          "    SUM(CASE WHEN vp.isAFavor = 1 THEN 1 ELSE 0 END), " + // Suma 1 si es a favor, 0 si no
//          "    SUM(CASE WHEN vp.isAFavor = 0 THEN 1 ELSE 0 END)" + // Suma 1 si es en contra, 0 si no
          ") " +
          "FROM Propuesta p " +
          "JOIN p.participanteromeria pr " + // Accede a ParticipanteRomeria
          "JOIN pr.usuario u " +  
          "JOIN pr.year y " +              // Accede al Year desde ParticipanteRomeria
          "JOIN y.casa c " +               // Accede a la Casa desde Year
          "LEFT JOIN p.votopropuestas vp " + // LEFT JOIN para incluir propuestas sin votos.
                                             // Asegúrate que 'votoPropuestas' es el nombre de la colección OneToMany en tu entidad Propuesta.
          "WHERE y.isActive = TRUE AND c.id = :idCasa " + // Filtra por año activo y ID de Casa
          "GROUP BY p.id, pr.usuario.nombre, p.fecha") // Agrupa por el ID de la Propuesta para sumar sus votos
   List<PropuestasListDTO> findPropuestasWithVoteSummaryByActiveYearAndCasaId(@Param("idCasa") int idCasa);


   

   // Método para obtener la información básica de las propuestas (no cambia)
   @Query("SELECT NEW com.boot.DTO.PropuestasListDTO(" +
          "    p.id, " +
          "    u.nombre, " +
          "    p.fecha" +
          ") " +
          "FROM Propuesta p " +
          "JOIN p.participanteromeria pr " +
          "JOIN pr.usuario u " +
          "JOIN pr.year y " +
          "JOIN y.casa c " +
          "WHERE y.isActive = TRUE AND c.id = :idCasa " +
          "GROUP BY p.id, u.nombre, p.fecha")
   List<PropuestasListDTO> findBasicPropuestasByActiveYearAndCasaId(@Param("idCasa") int idCasa);


   // Métodos para obtener los nombres de los votantes (no cambian)
   @Query("SELECT u.nombre FROM Votopropuesta vp " +
          "JOIN vp.participanteromeria pr " +
          "JOIN pr.usuario u " +
          "WHERE vp.propuesta.id = :propuestaId AND vp.isAFavor = 1")
   List<String> findVotersNamesAFavorByPropuestaId(@Param("propuestaId") int propuestaId);

   @Query("SELECT u.nombre FROM Votopropuesta vp " +
          "JOIN vp.participanteromeria pr " +
          "JOIN pr.usuario u " +
          "WHERE vp.propuesta.id = :propuestaId AND vp.isAFavor = 0")
   List<String> findVotersNamesEnContraByPropuestaId(@Param("propuestaId") int propuestaId);
   
   
   
	List<Votopropuesta> findByPropuestaId(Long propuestaId);


}
