package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Habitacion;
import com.boot.model.Propuesta;
import com.boot.model.Propuestaasignacion;

public interface PropuestaAsignacionRepository extends JpaRepository<Propuestaasignacion, Integer>{
	Propuestaasignacion findByPropuestaAndHabitacionAndPersona(Propuesta p, Habitacion h, String s);
	Propuestaasignacion findByPropuestaAndPersona(Propuesta p, String s);
	List<Propuestaasignacion> findAllByPropuestaAndHabitacion(Propuesta p, Habitacion h);
	List<Propuestaasignacion> findByPropuestaId(Long propuestaId);

}
