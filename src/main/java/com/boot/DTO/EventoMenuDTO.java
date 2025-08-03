package com.boot.DTO;

import com.boot.model.Year;

public class EventoMenuDTO {

    private int id;
    private int year;
    private String nombre;

    public EventoMenuDTO(Year entity) {
        this.id = entity.getId();
        this.year = entity.getYear();
        this.nombre = entity.getNombre();
    }
    
    // Getters
    public int getId() { return id; }
    public int getYear() { return year; }
    public String getNombre() { return nombre; }
}