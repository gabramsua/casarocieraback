package com.boot.controller;

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

import com.boot.model.Participantecomida;
import com.boot.model.Participanteromeria;
import com.boot.model.Turnocomida;
import com.boot.pojo.CustomError;
import com.boot.repository.ParticipanteComidaRepository;
import com.boot.repository.ParticipanteRomeriaRepository;
import com.boot.repository.TurnoComidaRepository;



@RestController
public class ParticipanteComidaController {

	@Autowired
	ParticipanteComidaRepository repository;
	@Autowired
	TurnoComidaRepository turnoComidaRepository;
	@Autowired
	ParticipanteRomeriaRepository participanteRepository;

//    @Operation(summary = "Devuelve el detalle de un participantecomida")
	@CrossOrigin
	@GetMapping(value="participantecomida/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Participantecomida> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Participante de Comida con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de participantecomida")
	@CrossOrigin
	@GetMapping(value="/participantecomidas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un participantecomida")
	@CrossOrigin
	@PostMapping(value="participantecomida", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Participantecomida item) {
//		Participantecomida created_item    = new Participantecomida(item.getParticipanteromeria(), item.getTurnocomida());
		Participantecomida duplicated_item = new Participantecomida();
		
		//  Comprobaciones obligatorias
        if (String.valueOf(item.getParticipanteromeria()) == null || String.valueOf(item.getParticipanteromeria()).isEmpty() || String.valueOf(item.getParticipanteromeria()).isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'participante' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getTurnocomida()) == null || String.valueOf(item.getTurnocomida()).isEmpty() || String.valueOf(item.getTurnocomida()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'turnocomida' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        

        // Comprobamos que las entidades FK existen
        Optional<Turnocomida> turno = turnoComidaRepository.findById(item.getTurnocomida().getId());
        Optional<Participanteromeria> participante = participanteRepository.findById(item.getParticipanteromeria().getId());
        
    	if(turno.isPresent()) {
    		if(participante.isPresent()) {
		        //  Comprobaciones duplicidad
		        duplicated_item = repository.findByParticipanteromeriaAndTurnocomida(participante.get(), turno.get());
		        if (!ObjectUtils.isEmpty(duplicated_item)){
					CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un turno de comida con esos participante.");
		            return ResponseEntity.badRequest().body(err);
		        }
		        
		        //	Recuperamos registro creado
		        return ResponseEntity.ok(repository.save(new Participantecomida(participante.get(), turno.get())));
    		} else {
    			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún participanteromeria con esos datos.");
    	        return ResponseEntity.badRequest().body(err);
    	    }
    	} else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún turno con esos datos.");
	        return ResponseEntity.badRequest().body(err);
	    }
	}

	// ESTE ENDPOINT NO DEBERÍA SER USADO
/*
//  @Operation(summary = "Actualiza todos los valores de un año")
	@CrossOrigin
	@PutMapping(value="participantecomida", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Turnocomida item) {
		Turnocomida updated_item = new Turnocomida(item.getNombre(), item.getYear());
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	// Comprobamos que las entidades FK existen
                Optional<Year> year = yearRepository.findById(item.getYear().getId());
                
            	if(year.isPresent()) {
	            	return ResponseEntity.ok(repository.save(item));
            	}
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún año con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un año con esos datos.");
            return ResponseEntity.badRequest( ).body(err);
        }
		return null;
	}
	*/


	//  @Operation(summary = "Borrado físico de un participantecomida")
	@CrossOrigin
	@DeleteMapping(value="participantecomida/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Participantecomida deleted_item = new Participantecomida();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún participante de comida con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
}