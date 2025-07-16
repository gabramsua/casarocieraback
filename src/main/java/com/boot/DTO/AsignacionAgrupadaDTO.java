package com.boot.DTO;

import java.util.List;

import com.boot.model.Habitacion;

public class AsignacionAgrupadaDTO {

    private Habitacion habitacion;
    private List<String> personas;
    
    public AsignacionAgrupadaDTO(Habitacion habitacion, List<String> personas) {
        this.habitacion = habitacion;
        this.personas = personas;
    }
    
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	public List<String> getPersonas() {
		return personas;
	}
	public void setPersonas(List<String> personas) {
		this.personas = personas;
	}
}
