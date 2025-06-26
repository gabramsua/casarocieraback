package com.boot.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the year database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"balances", "participanteromerias", "turnocomidas", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Year.findAll", query="SELECT y FROM Year y")
public class Year implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int year;
	private String nombre;

	//bi-directional many-to-one association to Balance
	@OneToMany(mappedBy="year")
	private List<Balance> balances;

	//bi-directional many-to-one association to Participanteromeria
	@OneToMany(mappedBy="year")
	private List<Participanteromeria> participanteromerias;

	//bi-directional many-to-one association to Turnocomida
	@OneToMany(mappedBy="year")
	private List<Turnocomida> turnocomidas;

	public Year() {
	}

	public Year(String nombre, int year) {
		this.nombre = nombre;
		this.year = year;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Balance> getBalances() {
		return this.balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Balance addBalance(Balance balance) {
		getBalances().add(balance);
		balance.setYear(this);

		return balance;
	}

	public Balance removeBalance(Balance balance) {
		getBalances().remove(balance);
		balance.setYear(null);

		return balance;
	}

	public List<Participanteromeria> getParticipanteromerias() {
		return this.participanteromerias;
	}

	public void setParticipanteromerias(List<Participanteromeria> participanteromerias) {
		this.participanteromerias = participanteromerias;
	}

	public Participanteromeria addParticipanteromeria(Participanteromeria participanteromeria) {
		getParticipanteromerias().add(participanteromeria);
		participanteromeria.setYear(this);

		return participanteromeria;
	}

	public Participanteromeria removeParticipanteromeria(Participanteromeria participanteromeria) {
		getParticipanteromerias().remove(participanteromeria);
		participanteromeria.setYear(null);

		return participanteromeria;
	}

	public List<Turnocomida> getTurnocomidas() {
		return this.turnocomidas;
	}

	public void setTurnocomidas(List<Turnocomida> turnocomidas) {
		this.turnocomidas = turnocomidas;
	}

	public Turnocomida addTurnocomida(Turnocomida turnocomida) {
		getTurnocomidas().add(turnocomida);
		turnocomida.setYear(this);

		return turnocomida;
	}

	public Turnocomida removeTurnocomida(Turnocomida turnocomida) {
		getTurnocomidas().remove(turnocomida);
		turnocomida.setYear(null);

		return turnocomida;
	}

}