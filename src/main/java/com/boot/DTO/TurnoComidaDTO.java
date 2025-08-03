package com.boot.DTO;

import com.boot.model.Turnocomida;

public class TurnoComidaDTO {

    private String horario;
    private String nombre;
    private String platoPrincipal;

    public TurnoComidaDTO(Turnocomida entity) {
        this.horario = entity.getHorario();
        this.nombre = entity.getNombre();
        this.platoPrincipal = entity.getPlatoPrincipal();
    }
    
    // Getters
    public String getHorario() { return horario; }
    public String getNombre() { return nombre; }
    public String getPlatoPrincipal() { return platoPrincipal; }
}