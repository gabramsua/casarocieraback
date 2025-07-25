package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"balances", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Balance
	@OneToMany(mappedBy="categoria")
	private List<Balance> balances;
	
	// bi-directional many-to-one association to Casa
	@ManyToOne
	@JoinColumn(name="idCasa") // El nombre de la columna FK en la DB
	private Casa casa; // Nombre del campo en la entidad Java
	

	public Categoria() {
	}

	public Categoria(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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
		balance.setCategoria(this);

		return balance;
	}

	public Balance removeBalance(Balance balance) {
		getBalances().remove(balance);
		balance.setCategoria(null);

		return balance;
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

}