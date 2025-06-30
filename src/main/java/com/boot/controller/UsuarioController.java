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

import com.boot.model.Usuario;
import com.boot.pojo.CustomError;
import com.boot.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository repository;

//    @Operation(summary = "Devuelve el detalle de un usuario")
    @CrossOrigin
	@GetMapping(value="usuario/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Usuario> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún usuario con esos datos.");
	        return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de usuarios")
    @CrossOrigin
	@GetMapping(value="/usuarios", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un usuario")
    @CrossOrigin
	@PostMapping(value="usuario", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Usuario item) {
		Usuario created_item    = new Usuario(item.getNombre(), item.getUltimaConexion(), item.getIsAdmin());
		Usuario duplicated_item = new Usuario();
		
		//  Comprobaciones obligatorias
        if (created_item.getNombre() == null || created_item.getNombre().isEmpty() || created_item.getNombre().isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'nombre' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }        

        //  Comprobaciones duplicidad
        duplicated_item = repository.findByNombre(created_item.getNombre());
        if (!ObjectUtils.isEmpty(duplicated_item)){
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un usuario con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
        
        repository.save(item);
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
	}

//  @Operation(summary = "Actualiza todos los valores de un año")
    @CrossOrigin
	@PutMapping(value="usuario", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Usuario item) {
		Usuario updated_item    = new Usuario(item.getNombre());
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	repository.save(item);
            	return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún usuario con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un usuario con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
		return null;
	}
	
//  @Operation(summary = "Borrado físico de un Usuario")
    @CrossOrigin
	@DeleteMapping(value="usuario/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Usuario deleted_item = new Usuario();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún usuario con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
}
