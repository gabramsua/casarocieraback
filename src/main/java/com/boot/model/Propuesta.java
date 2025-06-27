package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the propuesta database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"propuestaasignacions", "votopropuestas", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Propuesta.findAll", query="SELECT p FROM Propuesta p")
public class Propuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	//bi-directional many-to-one association to Participanteromeria
	@ManyToOne
	@JoinColumn(name="idParticipanteRomeria")
	private Participanteromeria participanteromeria;

	//bi-directional many-to-one association to Propuestaasignacion
	@OneToMany(mappedBy="propuesta")
	private List<Propuestaasignacion> propuestaasignacions;

	//bi-directional many-to-one association to Votopropuesta
	@OneToMany(mappedBy="propuesta")
	private List<Votopropuesta> votopropuestas;

	public Propuesta() {
	}

	public Propuesta(Date fecha, Participanteromeria participanteromeria) {
		super();
		this.fecha = fecha;
		this.participanteromeria = participanteromeria;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Participanteromeria getParticipanteromeria() {
		return this.participanteromeria;
	}

	public void setParticipanteromeria(Participanteromeria participanteromeria) {
		this.participanteromeria = participanteromeria;
	}

	public List<Propuestaasignacion> getPropuestaasignacions() {
		return this.propuestaasignacions;
	}

	public void setPropuestaasignacions(List<Propuestaasignacion> propuestaasignacions) {
		this.propuestaasignacions = propuestaasignacions;
	}

	public Propuestaasignacion addPropuestaasignacion(Propuestaasignacion propuestaasignacion) {
		getPropuestaasignacions().add(propuestaasignacion);
		propuestaasignacion.setPropuesta(this);

		return propuestaasignacion;
	}

	public Propuestaasignacion removePropuestaasignacion(Propuestaasignacion propuestaasignacion) {
		getPropuestaasignacions().remove(propuestaasignacion);
		propuestaasignacion.setPropuesta(null);

		return propuestaasignacion;
	}

	public List<Votopropuesta> getVotopropuestas() {
		return this.votopropuestas;
	}

	public void setVotopropuestas(List<Votopropuesta> votopropuestas) {
		this.votopropuestas = votopropuestas;
	}

	public Votopropuesta addVotopropuesta(Votopropuesta votopropuesta) {
		getVotopropuestas().add(votopropuesta);
		votopropuesta.setPropuesta(this);

		return votopropuesta;
	}

	public Votopropuesta removeVotopropuesta(Votopropuesta votopropuesta) {
		getVotopropuestas().remove(votopropuesta);
		votopropuesta.setPropuesta(null);

		return votopropuesta;
	}

}