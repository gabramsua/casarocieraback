package com.boot.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Balance;
import com.boot.model.Categoria;
import com.boot.model.Participanteromeria;
import com.boot.model.Year;

public interface BalanceRepository extends JpaRepository<Balance, Integer>{
	
	/**					TO BE REMOVED
     * Recupera los últimos 10 registros de Balance que cumplen las siguientes condiciones:
     * - isIngreso es falso (es decir, son gastos).
     * - El año asociado (a través de ParticipanteRomeria) está activo (isActive es true).
     * Los resultados se ordenan por fecha de forma descendente para obtener los más recientes.
     *
     * @return Una lista de los últimos 10 objetos Balance que cumplen los criterios.
     * 					TO BE REMOVED
     */
//    List<Balance> findTop10ByIsIngresoFalseAndCasaIdAndParticipanteromeria_Year_IsActiveTrueOrderByFechaDesc(int id);
    
    /**
     * Recupera los últimos 10 registros de Balance que cumplen las siguientes condiciones:
     * - isIngreso es falso (es decir, son gastos).
     * - El Year asociado al Balance (directamente) pertenece a una Casa específica (idCasa).
     * - El Year asociado está activo (isActive es true).
     * Los resultados se ordenan por fecha de forma descendente para obtener los más recientes.
     *
     * @param casaId El ID de la Casa a la que debe pertenecer el Year del balance.
     * @return Una lista de los últimos 10 objetos Balance que cumplen los criterios.
     */
    List<Balance> findTop10ByIsIngresoFalseAndYear_Casa_IdAndYear_IsActiveTrueOrderByFechaDesc(int casaId);


	List<Balance> findByParticipanteromeriaAndIsIngresoTrueAndImporteAndCategoriaAndFecha(
			Participanteromeria oldParticipante, BigDecimal oldImporte, Categoria categoriaAportacion, Date fecha);


    // Método para encontrar todas las entradas de Balance para un Year específico
    // La nomenclatura "participanteromeria.year" le dice a Spring Data JPA que navegue por la relación
    List<Balance> findByParticipanteromeria_Year(Year year);


	List<Balance> findByYear(Year year);

}
