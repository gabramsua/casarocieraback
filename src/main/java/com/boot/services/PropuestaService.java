package com.boot.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.DTO.PropuestaAsignacionCreacionDTO;
import com.boot.DTO.PropuestaCreacionDTO;
import com.boot.model.Habitacion;
import com.boot.model.Propuesta;
import com.boot.model.Propuestaasignacion;
import com.boot.repository.HabitacionRepository;
import com.boot.repository.PropuestaAsignacionRepository;
import com.boot.repository.PropuestaRepository;

@Service
public class PropuestaService {

    private final PropuestaRepository propuestaRepository;
    private final PropuestaAsignacionRepository propuestaAsignacionRepository;
    private final HabitacionRepository habitacionRepository; // Necesario para validar capacidad

    public PropuestaService(PropuestaRepository propuestaRepository,
                            PropuestaAsignacionRepository propuestaAsignacionRepository,
                            HabitacionRepository habitacionRepository) {
        this.propuestaRepository = propuestaRepository;
        this.propuestaAsignacionRepository = propuestaAsignacionRepository;
        this.habitacionRepository = habitacionRepository;
    }

    @Transactional // Asegura que toda la operación sea atómica
    public Propuesta crearPropuestaConAsignaciones(PropuestaCreacionDTO dto) {
        // 1. Crear la entidad Propuesta
        Propuesta nuevaPropuesta = new Propuesta();
        nuevaPropuesta.setParticipanteromeria(dto.getParticipante());
        nuevaPropuesta.setFecha(dto.getFecha());
        nuevaPropuesta = propuestaRepository.save(nuevaPropuesta); // Guardar para obtener el ID

        final int propuestaId = nuevaPropuesta.getId(); // Capturar el ID de la propuesta recién creada

        // 2. Validar capacidad de habitaciones (ejemplo simplificado)
        // Esto es crucial para tu lógica de negocio
        dto.getAsignaciones().stream()
                .collect(Collectors.groupingBy(PropuestaAsignacionCreacionDTO::getIdHabitacion, Collectors.counting()))
                .forEach((idHabitacion, count) -> {
                    // Aquí deberías cargar la habitación y verificar si count excede su capacidad
                    // Ejemplo: Habitacion habitacion = habitacionRepository.findById(idHabitacion).orElseThrow(...);
                    // if (count > habitacion.getCapacidad()) { throw new RuntimeException("Capacidad excedida para habitación " + idHabitacion); }

                	Optional<Habitacion> habitacion = habitacionRepository.findById(idHabitacion);
                	Optional<Propuesta> propuesta = propuestaRepository.findById(propuestaId);
                    if(habitacion.isPresent()) {
                    	// Todas las personas de esa habitacion en esta propuesta
                    	List<Propuestaasignacion> personasDeHabitacion = propuestaAsignacionRepository.findAllByPropuestaAndHabitacion(propuesta.get(), habitacion.get());
                    	if(personasDeHabitacion.size() < habitacion.get().getCapacidad()) {
                    		// res = repository.save(item); => HAPPY PATH -> do nothing
                    	} else {
                			throw new IllegalArgumentException("La habitación está llena");
                            
                    	}
                	} else {
            			throw new IllegalArgumentException("Esta habitación no existe");
                	}
                	
                	
                	
                	
                    System.out.println("Habitación " + idHabitacion + ": " + count + " asignaciones en esta propuesta.");
                });


        // 3. Crear y guardar las entidades PropuestaAsignacion
        List<Propuestaasignacion> asignaciones = dto.getAsignaciones().stream().map(asignacioCreacionDTO -> {
          Propuestaasignacion asignacion = new Propuestaasignacion();
          
          asignacion.setPropuesta(propuestaRepository.findById(propuestaId).get());
          asignacion.setHabitacion(habitacionRepository.findById(asignacioCreacionDTO.getIdHabitacion()).get());
          asignacion.setPersona(asignacioCreacionDTO.getNombrePersona());
          return asignacion;
        })
		.collect(Collectors.toList());

        propuestaAsignacionRepository.saveAll(asignaciones); // Guardar todas las asignaciones en un batch

        return nuevaPropuesta;
    }

}
