package com.boot.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;


/**
 * The persistent class for the participanteromeriacomida database table.
 * 
 */
@Entity
//@JsonIgnoreProperties({"participanteromeriacomidas", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Participantecomida.findAll", query="SELECT p FROM Participantecomida p")
public class Participantecomida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Participanteromeria
	@ManyToOne
	@JoinColumn(name="idParticipanteRomeria")
	private Participanteromeria participanteromeria;

	//bi-directional many-to-one association to Turnocomida
	@ManyToOne
	@JoinColumn(name="idTurnoComida")
	private Turnocomida turnocomida;

	public Participantecomida() {
	}
	
	public Participantecomida(Participanteromeria p, Turnocomida t) {
		this.participanteromeria = p;
		this.turnocomida = t;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Participanteromeria getParticipanteromeria() {
		return this.participanteromeria;
	}

	public void setParticipanteromeria(Participanteromeria participanteromeria) {
		this.participanteromeria = participanteromeria;
	}

	public Turnocomida getTurnocomida() {
		return this.turnocomida;
	}

	public void setTurnocomida(Turnocomida turnocomida) {
		this.turnocomida = turnocomida;
	}

}