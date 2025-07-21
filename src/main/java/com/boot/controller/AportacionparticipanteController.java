package com.boot.controller;

import java.math.BigDecimal;
import java.util.Date; // Asegúrate de importar Date
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; // Usas RestController a nivel de clase, no @RequestMapping

import com.boot.model.Aportacionparticipante;
import com.boot.model.Balance; // Importar la entidad Balance
import com.boot.model.Categoria; // Necesario para la categoría de Balance
import com.boot.model.Participanteromeria;
import com.boot.pojo.CustomError;
import com.boot.repository.AportacionparticipanteRepository;
import com.boot.repository.BalanceRepository; // Importar el repositorio de Balance
import com.boot.repository.CategoriaRepository; // Importar el repositorio de Categoria (asumo que lo tienes)
import com.boot.repository.ParticipanteRomeriaRepository;

@RestController // Mapeo de rutas a nivel de método, no de clase con @RequestMapping
public class AportacionparticipanteController {

    @Autowired
    private AportacionparticipanteRepository repository;

    @Autowired
    private ParticipanteRomeriaRepository participanteromeriaRepository;

    @Autowired
    private BalanceRepository balanceRepository; // Inyectar el repositorio de Balance

    @Autowired
    private CategoriaRepository categoriaRepository; // Inyectar el repositorio de Categoria

    // --- 1. Obtener todas las aportaciones (opcionalmente por ParticipanteRomeria) ---
    @CrossOrigin
    @GetMapping(value="aportacions", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Aportacionparticipante>> getAllAportaciones(
            @RequestParam(required = false) Integer idParticipanteRomeria) {
        if (idParticipanteRomeria != null) {
            Optional<Participanteromeria> participanteOpt = participanteromeriaRepository.findById(idParticipanteRomeria);
            if (participanteOpt.isPresent()) {
                return new ResponseEntity<>(repository.findByParticipanteRomeria(participanteOpt.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        }
    }

    // --- 2. Obtener una aportación por ID ---
    @CrossOrigin
    @GetMapping(value="aportacion/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Aportacionparticipante> getAportacionById(@PathVariable int id) {
        Optional<Aportacionparticipante> aportacion = repository.findById(id);
        return aportacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // --- 3. Crear una nueva aportación ---
    @CrossOrigin
    @PostMapping(value="aportacion", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAportacion(@RequestBody Aportacionparticipante aportacion) {

        // 1. Validar que el ParticipanteRomeria existe
        Optional<Participanteromeria> participanteOpt = participanteromeriaRepository.findById(aportacion.getParticipanteRomeria().getId());
        if (participanteOpt.isEmpty()) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "ParticipanteRomeria no válido.");
            return ResponseEntity.badRequest().body(err); // ParticipanteRomeria no válido
        }
        Participanteromeria participante = participanteOpt.get();
        aportacion.setParticipanteRomeria(participante);

        // 2. Determinar la Casa y, por extensión, la Categoría
        // Asumimos que idCasa está disponible en el ParticipanteRomeria a través del Year
        // Y que el ID de la categoría para la aportación es el mismo que el id de la casa
        if (participante.getYear() == null || participante.getYear().getCasa() == null) {
			CustomError err = new CustomError(HttpStatus.BAD_REQUEST, "Información de Casa/Año incompleta en ParticipanteRomeria.");
            return ResponseEntity.badRequest().body(err);  // Información de Casa/Año incompleta en ParticipanteRomeria
        }
        int idCasa = participante.getYear().getCasa().getId();
        int idCategoriaParaAportacion = idCasa; // La regla es que el ID de la categoría es el mismo que el ID de la casa

        // 3. Validar que la Categoría existe usando su ID
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(idCategoriaParaAportacion);
        if (categoriaOpt.isEmpty()) {
            // Esto es crucial: si la categoría no existe con ese ID, hay un problema en la configuración de datos.
            System.err.println("Error: No se encontró la categoría con ID: " + idCategoriaParaAportacion + ". Asegúrese de que las categorías con ID de Casa existen.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // O BAD_REQUEST, dependiendo de la política de errores
        }
        Categoria categoriaAportacion = categoriaOpt.get();


        // 4. Guardar la aportación del participante
        Aportacionparticipante savedAportacion = repository.save(aportacion);

        // --- Lógica para la tabla BALANCE (INSERCIÓN) ---
        try {
            Balance balanceEntry = new Balance();
            balanceEntry.setParticipanteromeria(participante);
            balanceEntry.setCategoria(categoriaAportacion); // Usar la categoría encontrada por el ID de la casa
            balanceEntry.setIsIngreso((byte) (1)); // Una aportación es un ingreso para la casa
            balanceEntry.setImporte(savedAportacion.getImporte().doubleValue());
            balanceEntry.setFecha(savedAportacion.getFecha());
            // Concepto más detallado
            String conceptoBalance = "Aportación Personal de " + participante.getUsuario().getNombre();
            if (savedAportacion.getConcepto() != null && !savedAportacion.getConcepto().isEmpty()) {
                conceptoBalance += " (" + savedAportacion.getConcepto() + ")";
            }
            balanceEntry.setConcepto(conceptoBalance);
            balanceEntry.setCasa(participante.getYear().getCasa()); // Asocia a la casa del año/participante

            balanceRepository.save(balanceEntry);

        } catch (Exception e) {
            System.err.println("Error al registrar la aportación en la tabla Balance: " + e.getMessage());
            // En un entorno de producción real, aquí deberías manejar la transacción para hacer un rollback
            // si la operación de Balance falla, para evitar inconsistencias de datos.
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 5. Actualizar totalAportado en Participanteromeria
        BigDecimal nuevoTotalAportado = participante.getTotalAportado().add(savedAportacion.getImporte());
        participante.setTotalAportado(nuevoTotalAportado);
        participanteromeriaRepository.save(participante);

        return new ResponseEntity<>(savedAportacion, HttpStatus.CREATED);
    }

    // --- 4. Actualizar una aportación existente ---
    @CrossOrigin
    @PutMapping(value="aportacion/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Aportacionparticipante> updateAportacion(@PathVariable int id, @RequestBody Aportacionparticipante aportacionDetails) {
        Optional<Aportacionparticipante> aportacionOpt = repository.findById(id);

        if (aportacionOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Aportación no encontrada
        }

        Aportacionparticipante existingAportacion = aportacionOpt.get();
        BigDecimal oldImporte = existingAportacion.getImporte();
        Participanteromeria oldParticipante = existingAportacion.getParticipanteRomeria(); // Necesitamos el participante original

        // 1. Actualiza los campos de la aportación
        existingAportacion.setImporte(aportacionDetails.getImporte());
        existingAportacion.setFecha(aportacionDetails.getFecha());
        existingAportacion.setConcepto(aportacionDetails.getConcepto());

        // Si el participante asociado a la aportación cambia, esto requeriría una lógica más compleja
        // que implicaría ajustar el totalAportado de dos participantes (el antiguo y el nuevo).
        // Para la mayoría de los casos de uso, una aportación no cambia de participante.
        // Si `aportacionDetails.getParticipanteRomeria()` es diferente a `existingAportacion.getParticipanteRomeria()`,
        // deberías manejar ese escenario aquí o prohibirlo si no es una operación permitida.
        // Por simplicidad, asumimos que el ParticipanteRomeria asociado NO cambia en una actualización.
        Participanteromeria currentParticipante = existingAportacion.getParticipanteRomeria();


        // 2. Guardar la aportación participante actualizada
        Aportacionparticipante updatedAportacion = repository.save(existingAportacion);

        // --- LÓGICA PARA LA TABLA BALANCE (ACTUALIZACIÓN) ---
        try {
            // Obtenemos el ID de la Categoría que debe ser igual al ID de la Casa
            if (currentParticipante.getYear() == null || currentParticipante.getYear().getCasa() == null) {
                System.err.println("Error: Información de Casa/Año incompleta en ParticipanteRomeria para la aportación ID: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            int idCategoriaParaAportacion = currentParticipante.getYear().getCasa().getId();

            // Validar que la Categoría existe (esto solo es una comprobación, no para crearla)
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(idCategoriaParaAportacion);
            if (categoriaOpt.isEmpty()) {
                System.err.println("Error: No se encontró la categoría con ID: " + idCategoriaParaAportacion + " para la aportación ID: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Categoria categoriaAportacion = categoriaOpt.get();

            // **** MÉTODO ROBUSTO PARA ENCONTRAR EL BALANCE A ACTUALIZAR (RECOMENDACIÓN FUERTE) ****
            // Si la tabla Balance tiene una FK o campo que almacena `idAportacionParticipante`
            // Balance balanceToUpdate = balanceRepository.findByAportacionParticipanteId(id).orElse(null);
            // if (balanceToUpdate != null) { ... }

            // **** MÉTODO MENOS ROBUSTO SI NO HAY FK DIRECTA (USANDO EL MÉTODO ACTUAL) ****
            // Esto es propenso a errores si hay múltiples aportaciones con el mismo importe antiguo, fecha y participante.
            // La búsqueda debería ser más específica o depender de la FK en Balance.
            List<Balance> balances = balanceRepository.findByParticipanteromeriaAndIsIngresoTrueAndImporteAndCategoriaAndFecha(
                                            oldParticipante, oldImporte, categoriaAportacion, existingAportacion.getFecha()); // Se busca con el importe antiguo y la fecha original

            if (!balances.isEmpty()) {
                Balance balanceToUpdate = balances.get(0); // Tomamos la primera coincidencia
                balanceToUpdate.setImporte(updatedAportacion.getImporte().doubleValue()); // Actualiza al nuevo importe
                balanceToUpdate.setFecha(updatedAportacion.getFecha());     // Actualiza a la nueva fecha
                String conceptoBalance = "Aportación de " + updatedAportacion.getParticipanteRomeria().getUsuario().getNombre();
                if (updatedAportacion.getConcepto() != null && !updatedAportacion.getConcepto().isEmpty()) {
                    conceptoBalance += " (Actualizada: " + updatedAportacion.getConcepto() + ")";
                }
                balanceToUpdate.setConcepto(conceptoBalance);
                balanceRepository.save(balanceToUpdate);
            } else {
                System.err.println("Advertencia: No se encontró una entrada de Balance correspondiente para la aportación ID " + id + ". Posible inconsistencia de datos o la lógica de búsqueda es insuficiente.");
                // En un escenario real, aquí podrías decidir si crear una nueva entrada de balance o qué hacer.
                // Si la lógica es que SIEMPRE DEBE EXISTIR una entrada de balance, esto sería un error.
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar la aportación en la tabla Balance (ID: " + id + "): " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // --------------------------------------------------

        // 3. Actualizar totalAportado en Participanteromeria si el importe ha cambiado
        if (oldImporte.compareTo(updatedAportacion.getImporte()) != 0) {
            BigDecimal nuevoTotalAportado = currentParticipante.getTotalAportado().subtract(oldImporte).add(updatedAportacion.getImporte());
            currentParticipante.setTotalAportado(nuevoTotalAportado);
            participanteromeriaRepository.save(currentParticipante);
        }

        return new ResponseEntity<>(updatedAportacion, HttpStatus.OK);
    }
    // --- 5. Eliminar una aportación ---
    @CrossOrigin
    @DeleteMapping(value="aportacion/{id}")
    public ResponseEntity<HttpStatus> deleteAportacion(@PathVariable int id) {
        try {
            Optional<Aportacionparticipante> aportacionOpt = repository.findById(id);

            if (aportacionOpt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Aportación no encontrada
            }

            Aportacionparticipante aportacionToDelete = aportacionOpt.get();
            Participanteromeria participante = aportacionToDelete.getParticipanteRomeria();

            // --- LÓGICA PARA LA TABLA BALANCE (ELIMINACIÓN) ---
            try {
                // Obtenemos el ID de la Categoría que debe ser igual al ID de la Casa del participante
                if (participante == null || participante.getYear() == null || participante.getYear().getCasa() == null) {
                    System.err.println("Error: Información de Casa/Año incompleta en ParticipanteRomeria para eliminar la aportación ID: " + id);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                int idCategoriaParaAportacion = participante.getYear().getCasa().getId();

                // Validar que la Categoría existe
                Optional<Categoria> categoriaOpt = categoriaRepository.findById(idCategoriaParaAportacion);
                if (categoriaOpt.isEmpty()) {
                    System.err.println("Error: No se encontró la categoría con ID: " + idCategoriaParaAportacion + " para eliminar la aportación ID: " + id);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                Categoria categoriaAportacion = categoriaOpt.get();

                // **** MÉTODO ROBUSTO PARA ENCONTRAR EL BALANCE A ELIMINAR (RECOMENDACIÓN FUERTE) ****
                // Si la tabla Balance tiene una FK o campo que almacena `idAportacionParticipante`
                // Optional<Balance> balanceToDeleteOpt = balanceRepository.findByAportacionParticipanteId(id);
                // if (balanceToDeleteOpt.isPresent()) { Balance balanceToDelete = balanceToDeleteOpt.get(); balanceRepository.delete(balanceToDelete); }

                // **** MÉTODO MENOS ROBUSTO SI NO HAY FK DIRECTA (USANDO EL MÉTODO ACTUAL) ****
                // Esto es propenso a errores si hay múltiples aportaciones con el mismo importe, fecha y participante.
                // La búsqueda debería ser más específica o depender de la FK en Balance.
                List<Balance> balances = balanceRepository.findByParticipanteromeriaAndIsIngresoTrueAndImporteAndCategoriaAndFecha(
                                                participante, aportacionToDelete.getImporte(), categoriaAportacion, aportacionToDelete.getFecha());

                if (!balances.isEmpty()) {
                    Balance balanceToDelete = balances.get(0); // Tomamos la primera coincidencia
                    balanceRepository.delete(balanceToDelete);
                } else {
                    System.err.println("Advertencia: No se encontró una entrada de Balance correspondiente para eliminar la aportación ID " + id + ". Posible inconsistencia de datos o la lógica de búsqueda es insuficiente.");
                }

            } catch (Exception e) {
                System.err.println("Error al eliminar la aportación de la tabla Balance (ID: " + id + "): " + e.getMessage());
                // En un entorno de producción, considera lanzar una excepción, revertir la transacción o manejar esto de forma más robusta.
                // Idealmente, esto debería estar en una transacción para revertir la eliminación de la aportación si falla el balance.
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // --------------------------------------------------

            // Antes de eliminar la aportación, restar el importe de totalAportado del Participanteromeria
            if (participante != null) {
                BigDecimal nuevoTotalAportado = participante.getTotalAportado().subtract(aportacionToDelete.getImporte());
                participante.setTotalAportado(nuevoTotalAportado);
                participanteromeriaRepository.save(participante);
            }

            repository.delete(aportacionToDelete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            System.err.println("Error general al eliminar la aportación ID: " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}