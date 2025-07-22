package com.boot.DTO;

import java.util.Date;
import java.util.List;

import com.boot.model.Participanteromeria;


public class PropuestaCreacionDTO {


//    @NotNull(message = "El ID del participante no puede ser nulo")
    private Participanteromeria participante;

//    @PastOrPresent(message = "La fecha de la propuesta no puede ser futura")
//    @NotNull(message = "La fecha de la propuesta no puede ser nula")
    private Date fecha;

//    @NotEmpty(message = "La propuesta debe contener al menos una asignación")
//    @Valid // Importante: Valida también los objetos dentro de la lista
    private List<PropuestaAsignacionCreacionDTO> asignaciones;

    // Constructor(es)
    public PropuestaCreacionDTO() {
    }

    public PropuestaCreacionDTO(Participanteromeria participante, Date fecha, List<PropuestaAsignacionCreacionDTO> asignaciones) {
        this.participante = participante;
        this.fecha = fecha;
        this.asignaciones = asignaciones;
    }

    // Getters y Setters
    public Participanteromeria getParticipante() {
        return participante;
    }

    public void setParticipante(Participanteromeria participante) {
        this.participante = participante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<PropuestaAsignacionCreacionDTO> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<PropuestaAsignacionCreacionDTO> asignaciones) {
        this.asignaciones = asignaciones;
    }
}
