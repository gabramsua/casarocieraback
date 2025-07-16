package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the habitacion database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"propuestaasignacions", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Habitacion.findAll", query="SELECT h FROM Habitacion h")
public class Habitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int capacidad;

	private String nombre;

	//bi-directional many-to-one association to Propuestaasignacion
	@OneToMany(mappedBy="habitacion")
	private List<Propuestaasignacion> propuestaasignacions;

	// bi-directional many-to-one association to Casa
	@ManyToOne
	@JoinColumn(name="idCasa") // El nombre de la columna FK en la DB
	private Casa casa; // Nombre del campo en la entidad Java
	
	public Habitacion() {
	}

	public Habitacion(int capacidad, String nombre) {
		super();
		this.capacidad = capacidad;
		this.nombre = nombre;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Propuestaasignacion> getPropuestaasignacions() {
		return this.propuestaasignacions;
	}

	public void setPropuestaasignacions(List<Propuestaasignacion> propuestaasignacions) {
		this.propuestaasignacions = propuestaasignacions;
	}

	public Propuestaasignacion addPropuestaasignacion(Propuestaasignacion propuestaasignacion) {
		getPropuestaasignacions().add(propuestaasignacion);
		propuestaasignacion.setHabitacion(this);

		return propuestaasignacion;
	}

	public Propuestaasignacion removePropuestaasignacion(Propuestaasignacion propuestaasignacion) {
		getPropuestaasignacions().remove(propuestaasignacion);
		propuestaasignacion.setHabitacion(null);

		return propuestaasignacion;
	}


	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}
}