package com.boot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Casa;
import com.boot.pojo.CustomError;
import com.boot.repository.CasaRepository;

@RestController
public class CasaController {

	@Autowired
	CasaRepository repository;

	@CrossOrigin
	@GetMapping(value="casa/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Casa> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna Casa con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}

	@CrossOrigin
	@GetMapping(value="/casas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	// No vamos a añadir, editar ni borrar casas mediante un endpoint...
}
