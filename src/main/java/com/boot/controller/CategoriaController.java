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

import com.boot.model.Categoria;
import com.boot.repository.CategoriaRepository;

@RestController
public class CategoriaController {

	@Autowired
	CategoriaRepository repository;

//    @Operation(summary = "Devuelve el detalle de una C")
	@GetMapping(value="categoria/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable("id") int id) {
		return ResponseEntity.ok(repository.findById(id).orElse(null));
	}
	
//  @Operation(summary = "Devuelve el listado de categorias")
	@GetMapping(value="/categorias", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade una categoria")
	@PostMapping(value="categoria", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> add(@RequestBody Categoria item) {
		Categoria created_item    = new Categoria(item.getNombre());
		Categoria duplicated_item = new Categoria(item.getNombre());
		
		//  Comprobaciones obligatorias
        if (created_item.getNombre() == null || created_item.getNombre().isEmpty() || created_item.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campo 'nombre' es obligatorio");
        }        

        //  Comprobaciones duplicidad
        duplicated_item = repository.findByNombre(created_item.getNombre());
        if (!ObjectUtils.isEmpty(duplicated_item)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La categoría indicada ya existe.");
        }
        
        repository.save(item);
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.findByNombre(item.getNombre()));
	}

//  @Operation(summary = "Actualiza todos los valores de una categoría")
	@PutMapping(value="categoria", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Categoria item) {
		Categoria updated_item    = new Categoria(item.getNombre());
		
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
	
//  @Operation(summary = "Borrado físico de una categoria")
	@DeleteMapping(value="categoria/{id}")
	public ResponseEntity<?> delete(@PathVariable("id")int id) {
		repository.delete(repository.findById(id).orElse(null));

		Categoria deleted_item = new Categoria();


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
