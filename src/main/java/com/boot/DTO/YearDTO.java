package com.boot.DTO;

import java.math.BigDecimal;

import com.boot.model.Year;

public class YearDTO {

    private Long id;
    private Integer year;
    private String nombre;
    private BigDecimal precio;
    private CasaDTO casa; // Usamos CasaDTO aquí
    private boolean active;

    public YearDTO(Year year) {
        this.id = (long) year.getId();
        this.year = year.getYear();
        this.nombre = year.getNombre();
        this.precio = year.getPrecio();
        this.active = year.isActive();
        if (year.getCasa() != null) {
            this.casa = new CasaDTO(year.getCasa());
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public CasaDTO getCasa() {
		return casa;
	}

	public void setCasa(CasaDTO casa) {
		this.casa = casa;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
