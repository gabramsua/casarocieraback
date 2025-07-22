package com.boot.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.Operation;

import com.boot.model.Habitacion;
import com.boot.model.Propuestaasignacion;
import com.boot.pojo.CustomError;
import com.boot.repository.HabitacionRepository;
import com.boot.repository.PropuestaAsignacionRepository;



@RestController
public class PropuestaAsignacionController {

	@Autowired
	PropuestaAsignacionRepository repository;
	@Autowired
	HabitacionRepository habitacionRepository;
//	VotoPropuestaRepository repository;

//    @Operation(summary = "Devuelve el detalle de una Asignacion propuesta")
	@CrossOrigin
	@GetMapping(value="propuestaAsignacion/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Propuestaasignacion> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Propuesta de Asignación con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de Asignaciones Propuestas")
	@CrossOrigin
	@GetMapping(value="/propuestaAsignacions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade una asignación propuesta")
	@CrossOrigin
	@PostMapping(value="propuestaAsignacion", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Propuestaasignacion item) {
		Propuestaasignacion duplicated_item = new Propuestaasignacion(item.getPersona(), item.getHabitacion(), item.getPropuesta());
		
		//  Comprobaciones obligatorias
		if (item.getPersona() == null || item.getPersona().isEmpty() || item.getPersona().isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'persona' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }if (item.getPropuesta() == null || String.valueOf(item.getPropuesta()).isEmpty() || String.valueOf(item.getPropuesta()).isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'propuesta' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getHabitacion()) == null || String.valueOf(item.getHabitacion()).isEmpty() || String.valueOf(item.getHabitacion()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'Habitación' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        
        //  Comprobaciones duplicidad
//        duplicated_item = repository.findByPropuestaAndHabitacionAndPersona(item.getPropuesta(), item.getHabitacion(), item.getPersona());
        duplicated_item = repository.findByPropuestaAndPersona(item.getPropuesta(), item.getPersona());
        if (!ObjectUtils.isEmpty(duplicated_item)){
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Esta persona ya está en esta propuesta");
//			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Esta persona ya está en esta habitación para esta propuesta");
            return ResponseEntity.badRequest().body(err);
        }
        
        Propuestaasignacion res = new Propuestaasignacion();
        // Comprobamos la capacidad de la habitación
        Optional<Habitacion> habitacion = habitacionRepository.findById(item.getHabitacion().getId());
        if(habitacion.isPresent()) {
        	// Todas las personas de esa habitacion en esta propuesta
        	List<Propuestaasignacion> personasDeHabitacion = repository.findAllByPropuestaAndHabitacion(item.getPropuesta(), item.getHabitacion());
        	if(personasDeHabitacion.size() < habitacion.get().getCapacidad()) {
        		res = repository.save(item);
        	} else {
    			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "La habitación está llena");
                return ResponseEntity.badRequest().body(err);
        	}
    	} else {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Esta habitación no existe");
            return ResponseEntity.badRequest().body(err);
    	}
        
        //	Recuperamos registro creado
        return ResponseEntity.ok(res);	
	}

	
	// NO TIENE SENTIDO EN ESTE CONTEXTO
//  @Operation(summary = "Actualiza todos los valores de un voto de propuesta")
//	@CrossOrigin
//	@PutMapping(value="propuestaAsignacion", consumes=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> update(@RequestBody Votopropuesta item) {
//		Votopropuesta updated_item    = new Votopropuesta();
//		
//		try {
//            // Buscar el registro por su ID en la base de datos
//			updated_item = repository.findById(item.getId()).get();
//            if (!ObjectUtils.isEmpty(updated_item)){
//            	return ResponseEntity.ok(repository.save(item));
//            }
//		} catch (NoSuchElementException e) {
//			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún voto de propuesta con esos datos.");
//            return ResponseEntity.badRequest().body(err);
//		} catch (Exception e) {			// ¿ NO HIT ?
//			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe una propuesta con esos datos.");
//            return ResponseEntity.badRequest().body(err);
//        }
//		return null;
//	}
	
//  @Operation(summary = "Borrado físico de un registro de propuesta asignación")
	@CrossOrigin
	@DeleteMapping(value="propuestaAsignacion/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Propuestaasignacion deleted_item = new Propuestaasignacion();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna Propuestaasignacion con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
}
