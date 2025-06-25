package com.boot.controller;

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

import com.boot.model.Usuario;
import com.boot.model.Year;
import com.boot.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository repository;

//    @Operation(summary = "Devuelve el detalle de un usuario")
	@GetMapping(value="usuario/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable("id") int id) {
		return ResponseEntity.ok(repository.findById(id).orElse(null));
	}
	
//  @Operation(summary = "Devuelve el listado de usuarios")
	@GetMapping(value="/usuarios", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un usuario")
	@PostMapping(value="usuario", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> add(@RequestBody Usuario item) {
		Usuario created_item    = new Usuario(item.getNombre(), item.getUltimaConexion(), item.getIsAdmin());
		Usuario duplicated_item = new Usuario();
		
		//  Comprobaciones obligatorias
        if (created_item.getNombre() == null || created_item.getNombre().isEmpty() || created_item.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campo 'nombre' es obligatorio");
        }        

        //  Comprobaciones duplicidad
        duplicated_item = repository.findByNombre(created_item.getNombre());
        if (!ObjectUtils.isEmpty(duplicated_item)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario indicado ya existe.");
        }
        
        repository.save(item);
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
	}

//  @Operation(summary = "Actualiza todos los valores de un año")
	@PutMapping(value="usuario", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Usuario item) {
		Usuario updated_item    = new Usuario(item.getNombre());
		
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
	
//  @Operation(summary = "Borrado físico de un Usuario")
	@DeleteMapping(value="usuario/{id}")
	public ResponseEntity<?> delete(@PathVariable("id")int id) {
		repository.delete(repository.findById(id).orElse(null));

		Usuario deleted_item = new Usuario();


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
