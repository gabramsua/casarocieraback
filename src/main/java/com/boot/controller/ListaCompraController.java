package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.ListaCompra;
import com.boot.repository.CasaRepository;
import com.boot.repository.ListaCompraRepository;

@RestController
public class ListaCompraController {


    @Autowired
    private ListaCompraRepository listaCompraRepository;
    
    @Autowired
    private CasaRepository casaRepository; // Asumo que ya tienes un repositorio para la entidad Casa

    // Obtener todas las listas de la compra para una Casa
    @GetMapping("/listasDeCasa/{casaId}")
	@CrossOrigin
    public ResponseEntity<List<ListaCompra>> getAllListasByCasaId(@PathVariable int casaId) {
        List<ListaCompra> listas = listaCompraRepository.findAllByCasaId(casaId);
        return ResponseEntity.ok(listas);
    }
    
    // Crear una nueva lista de la compra
	@PostMapping(value="listascompra", consumes=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ListaCompra> createLista(@RequestBody ListaCompra lista) {
        // Aseguramos que la casa exista antes de guardar
        return casaRepository.findById(lista.getCasa().getId()).map(casa -> {
            lista.setCasa(casa);
            ListaCompra nuevaLista = listaCompraRepository.save(lista);
            return ResponseEntity.ok(nuevaLista);
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Obtener una lista por ID
    @GetMapping("/lista/{id}")
	@CrossOrigin
    public ResponseEntity<ListaCompra> getListaById(@PathVariable int id) {
        return listaCompraRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar una lista existente
	@PutMapping(value="listascompra", consumes=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ListaCompra> updateLista(@RequestBody ListaCompra listaActualizada) {
        return listaCompraRepository.findById(listaActualizada.getId()).map(lista -> {
            lista.setNombre(listaActualizada.getNombre());
            lista.setEstado(listaActualizada.getEstado());
            // No cambiamos la Casa ni la fecha de creación en un update simple
            ListaCompra actualizada = listaCompraRepository.save(lista);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una lista
    @DeleteMapping("/{id}")
	@CrossOrigin
    public ResponseEntity<?> deleteLista(@PathVariable int id) {
        return listaCompraRepository.findById(id).map(lista -> {
            listaCompraRepository.delete(lista);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
