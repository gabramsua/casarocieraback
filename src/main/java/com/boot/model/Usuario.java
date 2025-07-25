package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"participanteromerias", "hibernateLazyInitializer", "handler"})
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte isAdmin;

	private String nombre;

	@Temporal(TemporalType.DATE)
	private Date ultimaConexion;

	//bi-directional many-to-one association to Participanteromeria
	@OneToMany(mappedBy="usuario")
	private List<Participanteromeria> participanteromerias;

	// bi-directional many-to-one association to Casa
	@ManyToOne
	@JoinColumn(name="idCasa") // El nombre de la columna FK en la DB
	private Casa casa; // Nombre del campo en la entidad Java
	
	public Usuario() {
	}

	public Usuario(String nombre, Date ultimaConexion, byte isAdmin) {
		this.nombre = nombre;
		this.ultimaConexion = ultimaConexion;
		this.isAdmin = isAdmin;
	}

	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getUltimaConexion() {
		return this.ultimaConexion;
	}

	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	public List<Participanteromeria> getParticipanteromerias() {
		return this.participanteromerias;
	}

	public void setParticipanteromerias(List<Participanteromeria> participanteromerias) {
		this.participanteromerias = participanteromerias;
	}

	public Participanteromeria addParticipanteromeria(Participanteromeria participanteromeria) {
		getParticipanteromerias().add(participanteromeria);
		participanteromeria.setUsuario(this);

		return participanteromeria;
	}

	public Participanteromeria removeParticipanteromeria(Participanteromeria participanteromeria) {
		getParticipanteromerias().remove(participanteromeria);
		participanteromeria.setUsuario(null);

		return participanteromeria;
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

}