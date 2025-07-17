package com.boot.DTO;

import java.sql.Date;

public class PropuestasListDTO {
	private int id; // ID de la Propuesta
	private String nombreUsuario; // Nombre del usuario que hizo la propuesta
	private Date fecha; // << AÑADIDO: Fecha de creación de la propuesta
	private long votosAFavor;
	private long votosEnContra;
	
	// << CONSTRUCTOR ACTUALIZADO >>
	// Incluye el nuevo campo 'fechaPropuesta'
	public PropuestasListDTO(int id, String nombreUsuario, Date fecha, long votosAFavor, long votosEnContra) {
	    this.id = id;
	    this.nombreUsuario = nombreUsuario;
	    this.fecha= fecha; // Asigna el nuevo campo
	    this.votosAFavor = votosAFavor;
	    this.votosEnContra = votosEnContra;
	}
	
	// Getters
	public int getId() {
	    return id;
	}
	
	public String getNombreUsuario() {
	    return nombreUsuario;
	}
	
	// << NUEVO GETTER >>
	public Date getFecha() {
	    return fecha;
	}
	
	public long getVotosAFavor() {
	    return votosAFavor;
	}
	
	public long getVotosEnContra() {
	    return votosEnContra;
	}
	
	// Setters (Si los necesitas)
	public void setId(int id) {
	    this.id = id;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
	    this.nombreUsuario = nombreUsuario;
	}
	
	// << NUEVO SETTER >>
	public void setFecha(Date fechaPropuesta) {
	    this.fecha= fechaPropuesta;
	}
	
	public void setVotosAFavor(long votosAFavor) {
	    this.votosAFavor = votosAFavor;
	}
	
	public void setVotosEnContra(long votosEnContra) {
	    this.votosEnContra = votosEnContra;
	}
     
}
