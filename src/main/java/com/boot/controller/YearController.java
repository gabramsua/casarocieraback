package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.Operation;

import com.boot.model.Year;
import com.boot.repository.YearRepository;

@RestController
public class YearController {

	@Autowired
	YearRepository repository;

//    @Operation(summary = "Devuelve el detalle de un año")
	@GetMapping(value="year/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable("id") int id) {
		return ResponseEntity.ok(repository.findById(id).orElse(null));
	}
	
//  @Operation(summary = "Devuelve el listado de años")
	@GetMapping(value="/years", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un año")
	@PostMapping(value="year", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> add(@RequestBody Year item) {
		Year created_item    = new Year(item.getNombre(), item.getYear());
		Year duplicated_item = new Year(item.getNombre(), item.getYear());
		
		//  Comprobaciones obligatorias
        if (created_item.getNombre() == null || created_item.getNombre().isEmpty() || created_item.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campo 'nombre' es obligatorio");
        }
        if (String.valueOf(created_item.getYear()) == null || String.valueOf(created_item.getYear()).isEmpty() || String.valueOf(created_item.getYear()).isBlank()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campo 'year' es obligatorio");
        }
        

        //  Comprobaciones duplicidad
        duplicated_item = repository.findByNombre(created_item.getNombre());
        if (!ObjectUtils.isEmpty(duplicated_item)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El año indicado ya existe.");
        }
        
        repository.save(item);
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
	}

//  @Operation(summary = "Actualiza todos los valores de un año")
	@PutMapping(value="year", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Year item) {
		Year updated_item    = new Year(item.getNombre(), item.getYear());
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	repository.save(item);
            	return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
            } else {
                return ResponseEntity.notFound().build();
            }
		} catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
	}
	
//  @Operation(summary = "Borrado físico de un año")
	@DeleteMapping(value="year/{id}")
	public ResponseEntity<?> delete(@PathVariable("id")int id) {
		repository.delete(repository.findById(id).orElse(null));

		Year deleted_item = new Year();


        // Buscamos la relación
		deleted_item = repository.findById(id).get();
        if (!ObjectUtils.isEmpty(deleted_item)){

            repository.deleteById(deleted_item.getId());
            
            return ResponseEntity.ok(repository.findAll());
        }else{
            return ResponseEntity.notFound().build();
        }
	}
}
