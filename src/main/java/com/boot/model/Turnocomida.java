package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the turnocomida database table.
 * 
 */
@JsonIgnoreProperties({"participanteromeriacomidas", "hibernateLazyInitializer", "handler"})
@Entity
@NamedQuery(name="Turnocomida.findAll", query="SELECT t FROM Turnocomida t")
public class Turnocomida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String horario;

	private String nombre;

	private String platoPrincipal;

	//bi-directional many-to-one association to Participanteromeriacomida
	@OneToMany(mappedBy="turnocomida")
	private List<Participanteromeriacomida> participanteromeriacomidas;

	//bi-directional many-to-one association to Year
	@ManyToOne
	@JoinColumn(name="idYear")
	private Year year;

	public Turnocomida() {
	}
	
	public Turnocomida(String nombre, Year year) {
		this.nombre = nombre;
		this.year = year;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHorario() {
		return this.horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPlatoPrincipal() {
		return this.platoPrincipal;
	}

	public void setPlatoPrincipal(String platoPrincipal) {
		this.platoPrincipal = platoPrincipal;
	}

	public List<Participanteromeriacomida> getParticipanteromeriacomidas() {
		return this.participanteromeriacomidas;
	}

	public void setParticipanteromeriacomidas(List<Participanteromeriacomida> participanteromeriacomidas) {
		this.participanteromeriacomidas = participanteromeriacomidas;
	}

	public Participanteromeriacomida addParticipanteromeriacomida(Participanteromeriacomida participanteromeriacomida) {
		getParticipanteromeriacomidas().add(participanteromeriacomida);
		participanteromeriacomida.setTurnocomida(this);

		return participanteromeriacomida;
	}

	public Participanteromeriacomida removeParticipanteromeriacomida(Participanteromeriacomida participanteromeriacomida) {
		getParticipanteromeriacomidas().remove(participanteromeriacomida);
		participanteromeriacomida.setTurnocomida(null);

		return participanteromeriacomida;
	}

	public Year getYear() {
		return this.year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

}