package com.boot.pojo;

import com.boot.model.Year;

public class EventoActivo {
	private Year year;
	private String nombreCasa;
	
	public EventoActivo(Year year, String nombreCasa) {
		this.year = year;
		this.nombreCasa = nombreCasa;
	}
	
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public String getNombreCasa() {
		return nombreCasa;
	}
	public void setNombreCasa(String nombreCasa) {
		this.nombreCasa = nombreCasa;
	}
	
	

}
