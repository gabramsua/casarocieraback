package com.boot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@Column(name = "isActive")
	private boolean isActive;
	@Column(name = "precio") // El nombre de la columna en la BD
	private BigDecimal precio; // Usamos BigDecimal para mapear DECIMAL(10,2)


	//bi-directional many-to-one association to Participanteromeria
	@OneToMany(mappedBy="year")
	private List<Participanteromeria> participanteromerias;

	//bi-directional many-to-one association to Turnocomida
	@OneToMany(mappedBy="year")
	private List<Turnocomida> turnocomidas;

	// bi-directional many-to-one association to Casa
	@ManyToOne
	@JoinColumn(name="idCasa") // El nombre de la columna FK en la DB
	private Casa casa; // Nombre del campo en la entidad Java

    // bi-directional many-to-one association to Balance
    @OneToMany(mappedBy="year")
    private List<Balance> balances;
	
	public Year() {
		// Inicializa el nuevo campo a 0.00 por defecto
		this.precio = BigDecimal.ZERO;
	}

	public Year(String nombre, int year) {
		this();
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public List<Balance> getBalances() {
		return balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

}