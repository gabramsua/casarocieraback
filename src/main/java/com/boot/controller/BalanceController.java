package com.boot.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

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

import com.boot.model.Balance;
import com.boot.model.Categoria;
import com.boot.model.Participanteromeria;
import com.boot.model.Year;
import com.boot.pojo.CustomError;
import com.boot.repository.BalanceRepository;
import com.boot.repository.CategoriaRepository;
import com.boot.repository.ParticipanteRomeriaRepository;

@RestController
public class BalanceController {

	@Autowired
	BalanceRepository repository;
	@Autowired
	ParticipanteRomeriaRepository participanteRepository;
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping(value="balance/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Balance> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Balance con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
	@GetMapping(value="/balances", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping(value="balance", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Balance item) {
		
		//  Comprobaciones obligatorias
        if (String.valueOf(item.getParticipanteromeria()) == null || String.valueOf(item.getParticipanteromeria()).isEmpty() || String.valueOf(item.getParticipanteromeria()).isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'participante' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getCategoria()) == null || String.valueOf(item.getCategoria()).isEmpty() || String.valueOf(item.getCategoria()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'categoría' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        

        // Comprobamos que las entidades FK existen
        Optional<Categoria> categoria = categoriaRepository.findById(item.getCategoria().getId());
        Optional<Participanteromeria> participante = participanteRepository.findById(item.getParticipanteromeria().getId());
        
    	if(categoria.isPresent()) {
    		if(participante.isPresent()) {
//		        //  Comprobaciones duplicidad => NO TIENE MUCHO SENTIDO COMPROBAR DUPLICIDADES EN ESTE CONTEXTO
//		        duplicated_item = repository.findByParticipanteromeriaAndTurnocomida(participante.get(), turno.get());
//		        if (!ObjectUtils.isEmpty(duplicated_item)){
//					CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un turno de comida con esos participante.");
//		            return ResponseEntity.badRequest().body(err);
//		        }
		        
		        //	Recuperamos registro creado
		        return ResponseEntity.ok(repository.save(new Balance(item.getConcepto(), item.getFecha(), item.getImporte(), item.getIsIngreso(), item.getUrlTicket(), categoria.get(), participante.get() )));
    		} else {
    			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún participanteromeria con esos datos.");
    	        return ResponseEntity.badRequest().body(err);
    	    }
    	} else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún turno con esos datos.");
	        return ResponseEntity.badRequest().body(err);
	    }
	}
	
	@PutMapping(value="balance", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Balance item) {
		Balance updated_item    = new Balance(item.getConcepto(), item.getFecha(), item.getImporte(), item.getIsIngreso(), item.getUrlTicket(), item.getCategoria(), item.getParticipanteromeria());

        // Buscar el registro por su ID en la base de datos
		updated_item = repository.findById(item.getId()).get();
        if (!ObjectUtils.isEmpty(updated_item)){
        	// Comprobamos que las entidades FK existen
            Optional<Categoria> categoria = categoriaRepository.findById(item.getCategoria().getId());
            Optional<Participanteromeria> participante = participanteRepository.findById(item.getParticipanteromeria().getId());
            
        	if(categoria.isPresent()) {
        		if(participante.isPresent()) {
        			return ResponseEntity.ok(repository.save(item));
        		}else {
        			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún ParticipanteRomeria con esos datos.");
                    return ResponseEntity.badRequest().body(err);
        		}
        	} else {
    			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ninguna categoría con esos datos.");
                return ResponseEntity.badRequest().body(err);
    		}
        }  else {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un año con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
	}
	
	@DeleteMapping(value="balance/{id}")
	public ResponseEntity<?> delete(@PathVariable()int id) {
		Balance deleted_item = new Balance();
		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún participante de comida con esos datos.");
            return ResponseEntity.badRequest().body(err);
		}
		return null;
	}
}
