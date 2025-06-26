package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the participanteromeria database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"balances", "participanteromeriacomidas", "propuestas", "votopropuestas", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Participanteromeria.findAll", query="SELECT p FROM Participanteromeria p")
public class Participanteromeria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Year
	@ManyToOne
	@JoinColumn(name="idYear")
	private Year year;

	//bi-directional many-to-one association to Balance
	@OneToMany(mappedBy="participanteromeria")
	private List<Balance> balances;

	//bi-directional many-to-one association to Participanteromeriacomida
	@OneToMany(mappedBy="participanteromeria")
	private List<Participanteromeriacomida> participanteromeriacomidas;

	//bi-directional many-to-one association to Propuesta
	@OneToMany(mappedBy="participanteromeria")
	private List<Propuesta> propuestas;

	//bi-directional many-to-one association to Votopropuesta
	@OneToMany(mappedBy="participanteromeria")
	private List<Votopropuesta> votopropuestas;

	public Participanteromeria() {
	}

	public Participanteromeria(Usuario usuario2, Year year2) {
		this.usuario = usuario2;
		this.year = year2;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Balance> getBalances() {
		return this.balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Balance addBalance(Balance balance) {
		getBalances().add(balance);
		balance.setParticipanteromeria(this);

		return balance;
	}

	public Balance removeBalance(Balance balance) {
		getBalances().remove(balance);
		balance.setParticipanteromeria(null);

		return balance;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Year getYear() {
		return this.year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public List<Participanteromeriacomida> getParticipanteromeriacomidas() {
		return this.participanteromeriacomidas;
	}

	public void setParticipanteromeriacomidas(List<Participanteromeriacomida> participanteromeriacomidas) {
		this.participanteromeriacomidas = participanteromeriacomidas;
	}

	public Participanteromeriacomida addParticipanteromeriacomida(Participanteromeriacomida participanteromeriacomida) {
		getParticipanteromeriacomidas().add(participanteromeriacomida);
		participanteromeriacomida.setParticipanteromeria(this);

		return participanteromeriacomida;
	}

	public Participanteromeriacomida removeParticipanteromeriacomida(Participanteromeriacomida participanteromeriacomida) {
		getParticipanteromeriacomidas().remove(participanteromeriacomida);
		participanteromeriacomida.setParticipanteromeria(null);

		return participanteromeriacomida;
	}

	public List<Propuesta> getPropuestas() {
		return this.propuestas;
	}

	public void setPropuestas(List<Propuesta> propuestas) {
		this.propuestas = propuestas;
	}

	public Propuesta addPropuesta(Propuesta propuesta) {
		getPropuestas().add(propuesta);
		propuesta.setParticipanteromeria(this);

		return propuesta;
	}

	public Propuesta removePropuesta(Propuesta propuesta) {
		getPropuestas().remove(propuesta);
		propuesta.setParticipanteromeria(null);

		return propuesta;
	}

	public List<Votopropuesta> getVotopropuestas() {
		return this.votopropuestas;
	}

	public void setVotopropuestas(List<Votopropuesta> votopropuestas) {
		this.votopropuestas = votopropuestas;
	}

	public Votopropuesta addVotopropuesta(Votopropuesta votopropuesta) {
		getVotopropuestas().add(votopropuesta);
		votopropuesta.setParticipanteromeria(this);

		return votopropuesta;
	}

	public Votopropuesta removeVotopropuesta(Votopropuesta votopropuesta) {
		getVotopropuestas().remove(votopropuesta);
		votopropuesta.setParticipanteromeria(null);

		return votopropuesta;
	}

}