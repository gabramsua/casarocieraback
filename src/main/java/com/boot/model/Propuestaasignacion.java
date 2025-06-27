package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the propuestaasignacion database table.
 * 
 */
@Entity
@NamedQuery(name="Propuestaasignacion.findAll", query="SELECT p FROM Propuestaasignacion p")
public class Propuestaasignacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String persona;

	//bi-directional many-to-one association to Habitacion
	@ManyToOne
	@JoinColumn(name="idHabitacion")
	private Habitacion habitacion;

	//bi-directional many-to-one association to Propuesta
	@ManyToOne
	@JoinColumn(name="idPropuesta")
	private Propuesta propuesta;

	public Propuestaasignacion() {
	}

	public Propuestaasignacion(String persona, Habitacion habitacion, Propuesta propuesta) {
		super();
		this.persona = persona;
		this.habitacion = habitacion;
		this.propuesta = propuesta;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPersona() {
		return this.persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public Habitacion getHabitacion() {
		return this.habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Propuesta getPropuesta() {
		return this.propuesta;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

}