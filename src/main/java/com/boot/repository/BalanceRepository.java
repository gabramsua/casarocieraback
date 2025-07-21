package com.boot.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Balance;
import com.boot.model.Categoria;
import com.boot.model.Participanteromeria;

public interface BalanceRepository extends JpaRepository<Balance, Integer>{
	
	/**
     * Recupera los últimos 10 registros de Balance que cumplen las siguientes condiciones:
     * - isIngreso es falso (es decir, son gastos).
     * - El año asociado (a través de ParticipanteRomeria) está activo (isActive es true).
     * Los resultados se ordenan por fecha de forma descendente para obtener los más recientes.
     *
     * @return Una lista de los últimos 10 objetos Balance que cumplen los criterios.
     */
    List<Balance> findTop10ByIsIngresoFalseAndCasaIdAndParticipanteromeria_Year_IsActiveTrueOrderByFechaDesc(int id);

	List<Balance> findByParticipanteromeriaAndIsIngresoTrueAndImporteAndCategoriaAndFecha(
			Participanteromeria oldParticipante, BigDecimal oldImporte, Categoria categoriaAportacion, Date fecha);


}
