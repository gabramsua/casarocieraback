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

import com.boot.model.Categoria;
import com.boot.repository.CategoriaRepository;
import com.boot.pojo.CustomError;

@RestController
public class CategoriaController {

	@Autowired
	CategoriaRepository repository;

//    @Operation(summary = "Devuelve el detalle de una Categoría")
	@CrossOrigin
	@GetMapping(value="categoria/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Categoria> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna categoría con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de categorias")
	@CrossOrigin
	@GetMapping(value="/categorias", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade una categoria")
	@CrossOrigin
	@PostMapping(value="categoria", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Categoria item) {
		Categoria created_item    = new Categoria(item.getNombre());
		Categoria duplicated_item = new Categoria(item.getNombre());
		
		//  Comprobaciones obligatorias
        if (created_item.getNombre() == null || created_item.getNombre().isEmpty() || created_item.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo 'nombre' es obligatorio");
        }        

        //  Comprobaciones duplicidad
        duplicated_item = repository.findByNombreAndCasa(created_item.getNombre(), item.getCasa());
        if (!ObjectUtils.isEmpty(duplicated_item)){
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un año con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
        
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.save(item));
	}

//  @Operation(summary = "Actualiza todos los valores de una categoría")
	@CrossOrigin
	@PutMapping(value="categoria", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Categoria item) {
		Categoria updated_item    = new Categoria(item.getNombre());
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findByNombreAndCasa(item.getNombre(), item.getCasa());
            if (!ObjectUtils.isEmpty(updated_item)){
            	return ResponseEntity.ok(repository.save(item));
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna categoría con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe una categoría con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
		return ResponseEntity.ok(repository.save(item));
	}
	
//  @Operation(summary = "Borrado físico de una categoria")
	@CrossOrigin
	@DeleteMapping(value="categoria/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Categoria deleted_item = new Categoria();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        } 
		} catch (NoSuchElementException e) {
				CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna categoría con esos datos.");
	            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
}
