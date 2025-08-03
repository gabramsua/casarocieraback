package com.boot.DTO;

import com.boot.model.Casa;

public class CasaDTO {

    private Long id;
    private String nombre;

    public CasaDTO(Casa casa) {
        this.id = (long) casa.getId();
        this.nombre = casa.getNombre();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
