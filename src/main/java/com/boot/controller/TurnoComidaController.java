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

import com.boot.model.Turnocomida;
import com.boot.model.Year;
import com.boot.pojo.CustomError;
import com.boot.repository.TurnoComidaRepository;
import com.boot.repository.YearRepository;



@RestController
public class TurnoComidaController {

	@Autowired
	TurnoComidaRepository repository;
	@Autowired
	YearRepository yearRepository;

//    @Operation(summary = "Devuelve el detalle de un TurnoComida")
	@CrossOrigin
	@GetMapping(value="turnocomida/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Turnocomida> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Turno de comida con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de TurnoComidas")
	@CrossOrigin
	@GetMapping(value="/turnocomidas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un Turnocomida")
	@PostMapping(value="turnocomida", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Turnocomida item) {
		Turnocomida created_item    = new Turnocomida(item.getNombre(), item.getYear());
		Turnocomida duplicated_item = new Turnocomida(item.getNombre(), item.getYear());
		
		//  Comprobaciones obligatorias
        if (created_item.getNombre() == null || created_item.getNombre().isEmpty() || created_item.getNombre().isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'nombre' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(created_item.getYear()) == null || String.valueOf(created_item.getYear()).isEmpty() || String.valueOf(created_item.getYear()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'year' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        

        // Comprobamos que las entidades FK existen
        Optional<Year> year = yearRepository.findById(item.getYear().getId());
        
    	if(year.isPresent()) {
	        //  Comprobaciones duplicidad
	        duplicated_item = repository.findByNombreAndYear(item.getNombre(), year.get());
	        if (!ObjectUtils.isEmpty(duplicated_item)){
				CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un turno de comida con esos datos.");
	            return ResponseEntity.badRequest().body(err);
	        }
	        
	        //	Recuperamos registro creado
	        return ResponseEntity.ok(repository.save(new Turnocomida(item.getNombre(), year.get())));
    	} else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún año con esos datos.");
	        return ResponseEntity.badRequest().body(err);
	    }
	}

//  @Operation(summary = "Actualiza todos los valores de un turno de comida")
	@CrossOrigin
	@PutMapping(value="turnocomida", consumes=MediaType.APPLICATION_JSON_VALUE)
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
            return ResponseEntity.badRequest().body(err);
        }
		return null;
	}
	
//  @Operation(summary = "Borrado físico de un turnocomida")
	@CrossOrigin
	@DeleteMapping(value="turnocomida/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Turnocomida deleted_item = new Turnocomida();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún turno de comida con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////
	///
	///						OTROS ENDPOINTS								///
	///
	///////////////////////////////////////////////////////////////////////
	
	@CrossOrigin
	@GetMapping(value="/comidasEventoActivo/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getComidasEventoActivo(@PathVariable() int id) {
        return ResponseEntity.ok(repository.findAllByYearIsActiveTrueAndYearCasaId(id));
	}
}
