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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.Operation;

import com.boot.model.Habitacion;
import com.boot.pojo.CustomError;
import com.boot.repository.HabitacionRepository;



@RestController
public class HabitacionController {

	@Autowired
	HabitacionRepository repository;

//    @Operation(summary = "Devuelve el detalle de un Habitacion")
	@CrossOrigin
	@GetMapping(value="habitacion/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Habitacion> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningna Habitacion con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de Habitacions")
	@CrossOrigin
	@GetMapping(value="/habitacions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un Habitacion")
	@CrossOrigin
	@PostMapping(value="habitacion", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Habitacion item) {
		Habitacion duplicated_item = new Habitacion(item.getCapacidad(), item.getNombre());
		
		//  Comprobaciones obligatorias
        if (item.getNombre() == null || item.getNombre().isEmpty() || item.getNombre().isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'nombre' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getCapacidad()) == null || String.valueOf(item.getCapacidad()).isEmpty() || String.valueOf(item.getCapacidad()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'capacidad' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        

        //  Comprobaciones duplicidad
        duplicated_item = repository.findByNombre(item.getNombre());
        if (!ObjectUtils.isEmpty(duplicated_item)){
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe una habitación con ese nombre.");
            return ResponseEntity.badRequest().body(err);
        }
        
        repository.save(item);
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.findByNombre(item.getNombre()));	
	}

//  @Operation(summary = "Actualiza todos los valores de un Habitacion")
	@CrossOrigin
	@PutMapping(value="habitacion", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Habitacion item) {
		Habitacion updated_item    = new Habitacion(item.getCapacidad(), item.getNombre());
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	repository.save(item);
            	return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna habitación con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe una habitación con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
		return null;
	}
	
//  @Operation(summary = "Borrado físico de un Habitacion")
	@CrossOrigin
	@DeleteMapping(value="habitacion/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Habitacion deleted_item = new Habitacion();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún año con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
}
