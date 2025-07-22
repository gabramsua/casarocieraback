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
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties({"usuarios","years","categorias", "habitaciones", "balances", "hibernateLazyInitializer", "handler"})
@Table(name="Casa") // Si el nombre de la tabla no es exactamente "Casa"
@NamedQuery(name="Casa.findAll", query="SELECT c FROM Casa c")
public class Casa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private boolean modTurnosComida;

	// bi-directional many-to-one association to Usuario
    @OneToMany(mappedBy="casa")
    private List<Usuario> usuarios;

    // bi-directional many-to-one association to Year
    @OneToMany(mappedBy="casa")
    private List<Year> years;

    // bi-directional many-to-one association to Categoria
    @OneToMany(mappedBy="casa")
    private List<Categoria> categorias;

    // bi-directional many-to-one association to Habitacion
    @OneToMany(mappedBy="casa")
    private List<Habitacion> habitaciones;


    // --- Constructores, Getters y Setters ---
    public Casa() {
    }
    


    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isModTurnosComida() {
		return modTurnosComida;
	}

	public void setModTurnosComida(boolean modTurnosComida) {
		this.modTurnosComida = modTurnosComida;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Year> getYears() {
		return years;
	}

	public void setYears(List<Year> years) {
		this.years = years;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

}
