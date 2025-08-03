package com.boot.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.Operation;

import com.boot.DTO.ParticipanteRomeriaActivoDTO;
import com.boot.model.Participanteromeria;
import com.boot.model.Usuario;
import com.boot.model.Year;
import com.boot.pojo.CustomError;
import com.boot.repository.ParticipanteRomeriaRepository;
import com.boot.repository.UsuarioRepository;
import com.boot.repository.YearRepository;

@RestController
public class ParticipanteRomeriaController {

	@Autowired
	ParticipanteRomeriaRepository repository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	YearRepository yearRepository;

//    @Operation(summary = "Devuelve el detalle de un participanteromeria")
	@CrossOrigin
	@GetMapping(value="participanteromeria/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		 Optional<Participanteromeria> item = repository.findById(id);

		    if (item.isPresent()) {
		        return ResponseEntity.ok(item.get());
		    } else {
				CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Participanteromeria con esos datos.");
	            return ResponseEntity.badRequest().body(err);
		    }
	}
	
//  @Operation(summary = "Devuelve el listado de participanteromeria")
	@CrossOrigin
	@GetMapping(value="/participanteromerias", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}

//  @Operation(summary = "Añade un participanteromeria")
	@CrossOrigin
	@PostMapping(value="participanteromeria", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Participanteromeria item) {
		Participanteromeria created_item    = new Participanteromeria(item.getUsuario(), item.getYear());
		Participanteromeria duplicated_item = new Participanteromeria(item.getUsuario(), item.getYear());
		
		//  Comprobaciones obligatorias
        if (item.getUsuario() == null) { //|| created_item.getUsuario().get.isEmpty() || created_item.getUsuario().isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'usuario' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getYear()) == null || String.valueOf(item.getYear()).isEmpty() || String.valueOf(item.getYear()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'year' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        
        // Comprobamos que las entidades FK existen
        Optional<Usuario> usuario = usuarioRepository.findById(item.getUsuario().getId());
        Optional<Year> year = yearRepository.findById(item.getYear().getId());

	    if (usuario.isPresent()) {
	    	if(year.isPresent()) {
	    	//  Comprobaciones duplicidad
	            duplicated_item = repository.findByUsuarioAndYear(created_item.getUsuario(), created_item.getYear());
	            if (!ObjectUtils.isEmpty(duplicated_item)){
	    			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El usuario indicado ya va ese año.");
	                return ResponseEntity.badRequest().body(err);
	            }
	            repository.save(new Participanteromeria(usuario.get(), year.get()));
	            //	Recuperamos registro creado
	            return ResponseEntity.ok(repository.findByUsuarioAndYear(created_item.getUsuario(), created_item.getYear()));    		
	    	} else {
				CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún año con esos datos.");
		        return ResponseEntity.badRequest().body(err);
		    }
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún usuario con esos datos.");
	        return ResponseEntity.badRequest().body(err);
	    }
        

        
	}
	
	// ESTE ENDPOINT NO DEBERÍA SER USADO
/*
//  @Operation(summary = "Actualiza todos los valores de un año")
	@CrossOrigin
	@PutMapping(value="participanteromeria", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Participanteromeria item) {
		Participanteromeria updated_item    = new Participanteromeria(item.getUsuario(), item.getYear());
		Participanteromeria duplicated_item = new Participanteromeria(item.getUsuario(), item.getYear());
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	// Comprobamos que las entidades FK existen
                Optional<Usuario> usuario = usuarioRepository.findById(item.getUsuario().getId());
                Optional<Year> year = yearRepository.findById(item.getYear().getId());

        	    if (usuario.isPresent()) {
        	    	if(year.isPresent()) {
        	    	//  Comprobaciones duplicidad
        	            duplicated_item = repository.findByUsuarioAndYear(usuario.get(), year.get());
        	            if (!ObjectUtils.isEmpty(duplicated_item)){
        	    			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El usuario indicado ya va ese año.");
        	                return ResponseEntity.badRequest().body(err);
        	            }
        	            repository.save(new Participanteromeria(usuario.get(), year.get()));
        	            //	Recuperamos registro creado
        	            return ResponseEntity.ok(repository.findByUsuarioAndYear(updated_item.getUsuario(), updated_item.getYear()));    		
        	    	} else {
        				CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún año con esos datos.");
        		        return ResponseEntity.badRequest().body(err);
        		    }
        	    } else {
        			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún usuario con esos datos.");
        	        return ResponseEntity.badRequest().body(err);
        	    }
            	
            	/*
            	repository.save(item);
            	return ResponseEntity.ok(repository.findByUsuarioAndYear(item.getUsuario(), item.getYear()));
            	*
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Participanteromeria con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe un Participanteromeria con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
		return null;
	}
	*/

	
//  @Operation(summary = "Borrado físico de un año")
	@CrossOrigin
	@DeleteMapping(value="participanteromeria/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Participanteromeria deleted_item = new Participanteromeria();

		try {
	        // Buscamos la relación
			deleted_item = repository.findById(id).get();
	        if (!ObjectUtils.isEmpty(deleted_item)){
	            repository.deleteById(deleted_item.getId());
	            
	            return ResponseEntity.ok(repository.findAll());
	        }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Participanteromeria con esos datos.");
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
	@GetMapping(value="/participantesActivos/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsuariosActivos(@PathVariable() int id) {
//        return ResponseEntity.ok(repository.findActiveParticipantsByCasaId(id));
        List<Participanteromeria> participantes = repository.findActiveParticipantsByCasaId(id);

        // 2. Mapear cada entidad ParticipanteRomeria a su DTO correspondiente
        List<ParticipanteRomeriaActivoDTO> dtos = participantes.stream()
            .map(participante -> {
                return new ParticipanteRomeriaActivoDTO(participante);
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
	}
	
}
