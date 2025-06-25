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

import com.boot.model.Year;
import com.boot.repository.YearRepository;

@RestController
public class YearController {

	@Autowired
	YearRepository repository;
	
	@GetMapping(value="year/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Year getItem(@PathVariable("id") int id) {
		return repository.findById(id).orElse(null);
	}
	@GetMapping(value="/years", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Year>getAll() {
		return repository.findAll();
	}
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
	
	@PutMapping(value="year", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody Year item) {
		repository.save(item);
	}
	@DeleteMapping(value="year/{id}")
	public void delete(@PathVariable("id")int id) {
		repository.delete(repository.findById(id).orElse(null));
	}
}
