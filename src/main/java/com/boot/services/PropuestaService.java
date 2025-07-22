package com.boot.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.DTO.AsignacionAgrupadaDTO;
import com.boot.DTO.DetallePropuestaDTO;
import com.boot.DTO.PropuestaAsignacionCreacionDTO;
import com.boot.DTO.PropuestaCreacionDTO;
import com.boot.model.Habitacion;
import com.boot.model.Propuesta;
import com.boot.model.Propuestaasignacion;
import com.boot.model.Votopropuesta;
import com.boot.repository.HabitacionRepository;
import com.boot.repository.PropuestaAsignacionRepository;
import com.boot.repository.PropuestaRepository;
import com.boot.repository.VotoPropuestaRepository;

@Service
public class PropuestaService {

    private final PropuestaRepository propuestaRepository;
    private final PropuestaAsignacionRepository propuestaAsignacionRepository;
    private final VotoPropuestaRepository votopropuestaRepository;
    private final HabitacionRepository habitacionRepository; // Necesario para validar capacidad

    public PropuestaService(PropuestaRepository propuestaRepository,
                            PropuestaAsignacionRepository propuestaAsignacionRepository,
                            VotoPropuestaRepository votopropuestaRepository,
                            HabitacionRepository habitacionRepository) {
        this.propuestaRepository = propuestaRepository;
        this.propuestaAsignacionRepository = propuestaAsignacionRepository;
        this.votopropuestaRepository = votopropuestaRepository;
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
    
    public DetallePropuestaDTO getDetallePropuesta(Long propuestaId) {
        List<Votopropuesta> votos = votopropuestaRepository.findByPropuestaId(propuestaId);
        List<Propuestaasignacion> asignaciones = propuestaAsignacionRepository.findByPropuestaId(propuestaId);

        List<String> votosAFavor = votos.stream()
        	    .filter(v -> v.getIsAFavor() == 1)
        	    .map(v -> v.getParticipanteromeria().getUsuario().getNombre())
        	    .toList();

        	List<String> votosEnContra = votos.stream()
        	    .filter(v -> v.getIsAFavor() == 0)
        	    .map(v -> v.getParticipanteromeria().getUsuario().getNombre())
        	    .toList();


        List<AsignacionAgrupadaDTO> asignacionDTOs = asignaciones.stream()
            .collect(Collectors.groupingBy(Propuestaasignacion::getHabitacion,
                Collectors.mapping(Propuestaasignacion::getPersona, Collectors.toList())))
            .entrySet()
            .stream()
            .map(e -> new AsignacionAgrupadaDTO(e.getKey(), e.getValue()))
            .toList();

        return new DetallePropuestaDTO(votosAFavor, votosEnContra, asignacionDTOs);
    }


//    public DetallePropuestaDTO getDetallePropuesta2(Long propuestaId) {
//        List<Propuestaasignacion> asignaciones = propuestaAsignacionRepository.findByPropuestaId(propuestaId);
//
//        Map<String, List<String>> agrupadas = asignaciones.stream()
//            .collect(Collectors.groupingBy(
//                a -> a.getHabitacion() != null ? a.getHabitacion().getNombre() : null,
//                Collectors.mapping(Propuestaasignacion::getPersona, Collectors.toList())
//            ));
//
//        List<AsignacionAgrupadaDTO> resultadoAgrupado = agrupadas.entrySet().stream()
//            .map(entry -> {
//                AsignacionAgrupadaDTO dto = new AsignacionAgrupadaDTO();
//                dto.setHabitacion(entry.getKey());
//                dto.setPersonas(entry.getValue());
//                return dto;
//            })
//            .collect(Collectors.toList());
//
//        DetallePropuestaDTO dto = new DetallePropuestaDTO();
//        dto.setVotosAFavor(votopropuestaRepository.findNombresVotosAFavor(propuestaId));
//        dto.setVotosEnContra(votopropuestaRepository.findNombresVotosEnContra(propuestaId));
//        dto.setAsignaciones(resultadoAgrupado);
//
//        return dto;
//    }

//    public DetallePropuestaDTO getDetallePropuesta(Long propuestaId) {
//        DetallePropuestaDTO dto = new DetallePropuestaDTO();
//
//        dto.setVotosAFavor(votopropuestaRepository.findNombresVotosAFavor(propuestaId));
//        dto.setVotosEnContra(votopropuestaRepository.findNombresVotosEnContra(propuestaId));
//        dto.setAsignaciones(propuestaAsignacionRepository.findByPropuestaId(propuestaId));
//
//        return dto;
//    }

}
