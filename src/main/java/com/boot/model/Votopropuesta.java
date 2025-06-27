package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the votopropuesta database table.
 * 
 */
@Entity
@NamedQuery(name="Votopropuesta.findAll", query="SELECT v FROM Votopropuesta v")
public class Votopropuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte isAFavor;

	//bi-directional many-to-one association to Participanteromeria
	@ManyToOne
	@JoinColumn(name="idParticipanteRomeria")
	private Participanteromeria participanteromeria;

	//bi-directional many-to-one association to Propuesta
	@ManyToOne
	@JoinColumn(name="idPropuesta")
	private Propuesta propuesta;

	public Votopropuesta() {
	}

	public Votopropuesta(Participanteromeria participanteromeria, Propuesta propuesta) {
		super();
		this.participanteromeria = participanteromeria;
		this.propuesta = propuesta;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getIsAFavor() {
		return this.isAFavor;
	}

	public void setIsAFavor(byte isAFavor) {
		this.isAFavor = isAFavor;
	}

	public Participanteromeria getParticipanteromeria() {
		return this.participanteromeria;
	}

	public void setParticipanteromeria(Participanteromeria participanteromeria) {
		this.participanteromeria = participanteromeria;
	}

	public Propuesta getPropuesta() {
		return this.propuesta;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

}