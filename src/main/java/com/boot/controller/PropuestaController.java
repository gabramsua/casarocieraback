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

import com.boot.DTO.PropuestaCreacionDTO;
import com.boot.model.Propuesta;
import com.boot.pojo.CustomError;
import com.boot.repository.PropuestaRepository;
import com.boot.services.PropuestaService;



@RestController
public class PropuestaController {

	@Autowired
	PropuestaRepository repository;
	
	@Autowired
    PropuestaService propuestaService;
	
//    @Operation(summary = "Devuelve el detalle de un Propuesta")
	@CrossOrigin
	@GetMapping(value="propuesta/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Propuesta> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna Propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de Propuestas")
	@CrossOrigin
	@GetMapping(value="/propuestas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade una Propuesta")
	@CrossOrigin
	@PostMapping(value="propuesta", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Propuesta item) {
		Propuesta duplicated_item = new Propuesta(item.getFecha(), item.getParticipanteromeria());
		
		//  Comprobaciones obligatorias
        if (item.getFecha() == null || String.valueOf(item.getFecha()).isEmpty() || String.valueOf(item.getFecha()).isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'fecha' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getParticipanteromeria()) == null || String.valueOf(item.getParticipanteromeria()).isEmpty() || String.valueOf(item.getParticipanteromeria()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'participanteromeria' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        
        // NO APLICA EN ESTE CONTEXTO
        //  Comprobaciones duplicidad
//        duplicated_item = repository.findByNombre(created_item.getNombre());
//        if (!ObjectUtils.isEmpty(duplicated_item)){
//			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un año con esos datos.");
//            return ResponseEntity.badRequest().body(err);
//        }
        
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.save(item));	
	}

//  @Operation(summary = "Actualiza todos los valores de un año")
	@CrossOrigin
	@PutMapping(value="propuesta", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Propuesta item) {
		Propuesta updated_item    = new Propuesta();
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	return ResponseEntity.ok(repository.save(item));
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe una propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
		return null;
	}
	
//  @Operation(summary = "Borrado físico de un año")
	@CrossOrigin
	@DeleteMapping(value="propuesta/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Propuesta deleted_item = new Propuesta();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
	
	
	/////////////////////////////////////////////// 
	///		RELLENAR LA PROPUESTA CREADA	   ///
	/////////////////////////////////////////////// 
	@CrossOrigin
	@PostMapping(value="rellenaPropuesta", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearPropuestaCompleta(@RequestBody PropuestaCreacionDTO propuestaCreacionDTO) {
        try {
            propuestaService.crearPropuestaConAsignaciones(propuestaCreacionDTO);
            return ResponseEntity.ok(repository.findAll());
//            return new ResponseEntity<>("Propuesta creada exitosamente.", HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo de errores más específico aquí (ej. PropuestaInvalidaException, CapacidadExcedidaException)
            return new ResponseEntity<>("Error al crear la propuesta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
