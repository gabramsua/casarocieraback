package com.boot.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.boot.DTO.PropuestasListDTO;
import com.boot.DTO.VotarPropuestaRequestDTO;
import com.boot.model.Votopropuesta;
import com.boot.pojo.CustomError;
import com.boot.repository.ParticipanteRomeriaRepository;
import com.boot.repository.PropuestaRepository;
import com.boot.repository.VotoPropuestaRepository;



@RestController
public class VotoPropuestaController {

	@Autowired
	VotoPropuestaRepository repository;
	@Autowired
    ParticipanteRomeriaRepository participanteromeriaRepository;
	@Autowired
    PropuestaRepository propuestaRepository;

//    @Operation(summary = "Devuelve el detalle de un VotoPropuesta")
	@CrossOrigin
	@GetMapping(value="votopropuesta/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItem(@PathVariable() int id) {
		Optional<Votopropuesta> item = repository.findById(id);

	    if (item.isPresent()) {
	        return ResponseEntity.ok(item.get());
	    } else {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún Voto de Propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
	    }
	}
	
//  @Operation(summary = "Devuelve el listado de Votopropuestas")
	@CrossOrigin
	@GetMapping(value="/votopropuestas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
	}
	
//  @Operation(summary = "Añade un Voto de propuesta")
	@CrossOrigin
	@PostMapping(value="votopropuesta", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Votopropuesta item) {		
		//  Comprobaciones obligatorias
        if (item.getPropuesta() == null || String.valueOf(item.getPropuesta()).isEmpty() || String.valueOf(item.getPropuesta()).isBlank()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'propuesta' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        if (String.valueOf(item.getParticipanteromeria()) == null || String.valueOf(item.getParticipanteromeria()).isEmpty() || String.valueOf(item.getParticipanteromeria()).isBlank()) {
        	CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "El campo 'participanteromeria' es obligatorio");
            return ResponseEntity.badRequest().body(err);
        }
        
        //  Comprobaciones duplicidad => TONTERÍA EN ESTE CASO
//        duplicated_item = repository.findByParticipanteromeriaAndPropuesta(item.getParticipanteromeria(), item.getPropuesta());
//        if (!ObjectUtils.isEmpty(duplicated_item)){
//			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Este usuario ya votó esta propuesta");
//            return ResponseEntity.badRequest().body(err);
//        }
        

	    Votopropuesta voto = new Votopropuesta();
	    voto.setIsAFavor((byte) (item.getIsAFavor()));
		voto.setParticipanteromeria(participanteromeriaRepository.findById(item.getParticipanteromeria().getId()).orElseThrow());
		voto.setPropuesta(propuestaRepository.findById(item.getPropuesta().getId()).orElseThrow());
	    
        //	Recuperamos registro creado
        return ResponseEntity.ok(repository.save(voto));	
	}

//  @Operation(summary = "Actualiza todos los valores de un voto de propuesta")
	@CrossOrigin
	@PutMapping(value="votopropuesta", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Votopropuesta item) {
		Votopropuesta updated_item    = new Votopropuesta();
		
		try {
            // Buscar el registro por su ID en la base de datos
			updated_item = repository.findById(item.getId()).get();
            if (!ObjectUtils.isEmpty(updated_item)){
            	return ResponseEntity.ok(repository.save(item));
            }
		} catch (NoSuchElementException e) {
			CustomError err = new CustomError(HttpStatus.NOT_FOUND, "No existe ningún voto de propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
		} catch (Exception e) {			// ¿ NO HIT ?
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Ya existe una propuesta con esos datos.");
            return ResponseEntity.badRequest().body(err);
        }
		return null;
	}
	
//  @Operation(summary = "Borrado físico de un año")
	@CrossOrigin
	@DeleteMapping(value="votopropuesta/{id}")
	public ResponseEntity<?> delete(@PathVariable() int id) {
		Votopropuesta deleted_item = new Votopropuesta();

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
	

	
	///////////////////////////////////////////////////////////////////////
	///
	///						OTROS ENDPOINTS								///
	///
	///////////////////////////////////////////////////////////////////////

	@CrossOrigin
	@GetMapping(value="/votopropuestasEventoActivo/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllEventoActivo(@PathVariable() int id) {
        List<PropuestasListDTO> basicPropuestas = repository.findBasicPropuestasByActiveYearAndCasaId(id);

        for (PropuestasListDTO propuestaDTO : basicPropuestas) {
            List<String> votantesAFavor = repository.findVotersNamesAFavorByPropuestaId(propuestaDTO.getId());
            propuestaDTO.setVotosAFavor(votantesAFavor != null ? votantesAFavor : new ArrayList<>()); // << CAMBIO AQUÍ

            List<String> votantesEnContra = repository.findVotersNamesEnContraByPropuestaId(propuestaDTO.getId());
            propuestaDTO.setVotosEnContra(votantesEnContra != null ? votantesEnContra : new ArrayList<>()); // << CAMBIO AQUÍ
        }

        return ResponseEntity.ok(basicPropuestas);
	}

	@CrossOrigin
	@PostMapping("/votar")
	public ResponseEntity<?> votar(@RequestBody VotarPropuestaRequestDTO dto) {
	    // Buscar si ya existe un voto para esa propuesta y ese participante
	    Optional<Votopropuesta> votoExistenteOpt = repository.findByPropuestaIdAndParticipanteromeriaId(
	        dto.getIdPropuesta(),
	        dto.getIdParticipanteromeria()
	    );
	
	    Votopropuesta voto = votoExistenteOpt.orElseGet(Votopropuesta::new);
	
	    voto.setIsAFavor((byte) (dto.isIsAFavor() ? 1 : 0));
	    voto.setParticipanteromeria(
	        participanteromeriaRepository.findById(dto.getIdParticipanteromeria()).orElseThrow()
	    );
	    voto.setPropuesta(
	        propuestaRepository.findById(dto.getIdPropuesta()).orElseThrow()
	    );
	
	    repository.save(voto);
	
	    return ResponseEntity.ok().build();
	}
}
