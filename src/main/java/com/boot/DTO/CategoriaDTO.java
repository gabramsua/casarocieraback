package com.boot.DTO;

import com.boot.model.Categoria;

public class CategoriaDTO {

    private Long id;
    private String nombre;
    private CasaDTO casa;

    public CategoriaDTO(Categoria categoria) {
        this.id = (long) categoria.getId();
        this.nombre = categoria.getNombre();
        if (categoria.getCasa() != null) {
            this.casa = new CasaDTO(categoria.getCasa());
        }
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

	public CasaDTO getCasa() {
		return casa;
	}

	public void setCasa(CasaDTO casa) {
		this.casa = casa;
	}
}
