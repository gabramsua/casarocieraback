package com.boot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
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
 * The persistent class for the participanteromeria database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"balances", "participanteromeriacomidas", "propuestas", "votopropuestas", "aportacionesParticipante", "hibernateLazyInitializer", "handler"})
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
	@Column(name = "isLogged") // o como se llame la columna
	private boolean isLogged;
	
	@Column(name = "costo") // El nombre de la columna en la BD si es diferente al nombre del campo
	private BigDecimal costo; // Usamos BigDecimal para mapear DECIMAL(10,2)

	@Column(name = "totalAportado") // El nombre de la columna en la BD si es diferente al nombre del campo
	private BigDecimal totalAportado; // Usamos BigDecimal para mapear DECIMAL(10,2)


	//bi-directional many-to-one association to Balance
	@OneToMany(mappedBy="participanteromeria")
	private List<Balance> balances;

	//bi-directional many-to-one association to Participanteromeriacomida
	@OneToMany(mappedBy="participanteromeria")
	private List<Participantecomida> participanteromeriacomidas;

	//bi-directional many-to-one association to Propuesta
	@OneToMany(mappedBy="participanteromeria")
	private List<Propuesta> propuestas;

	//bi-directional many-to-one association to Votopropuesta
	@OneToMany(mappedBy="participanteromeria")
	private List<Votopropuesta> votopropuestas;
	
	@OneToMany(mappedBy="participanteRomeria", cascade = CascadeType.ALL, orphanRemoval = true) // 'cascade' y 'orphanRemoval' si quieres gestionar la vida de las aportaciones desde aquí
	private List<Aportacionparticipante> aportacionesParticipante; // Nombre del campo en la entidad Java (plural)


	public Participanteromeria() {
		// Inicializa los nuevos campos a 0.00 por defecto para evitar NullPointerExceptions
		this.costo = BigDecimal.ZERO;
		this.totalAportado = BigDecimal.ZERO;
	}

	public Participanteromeria(Usuario usuario2, Year year2) {
		this(); // Llama al constructor por defecto para inicializar los BigDecimal
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


	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	public List<Participantecomida> getParticipanteromeriacomidas() {
		return this.participanteromeriacomidas;
	}

	public void setParticipanteromeriacomidas(List<Participantecomida> participanteromeriacomidas) {
		this.participanteromeriacomidas = participanteromeriacomidas;
	}

	public Participantecomida addParticipanteromeriacomida(Participantecomida participanteromeriacomida) {
		getParticipanteromeriacomidas().add(participanteromeriacomida);
		participanteromeriacomida.setParticipanteromeria(this);

		return participanteromeriacomida;
	}

	public Participantecomida removeParticipanteromeriacomida(Participantecomida participanteromeriacomida) {
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
	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getTotalAportado() {
		return totalAportado;
	}

	public void setTotalAportado(BigDecimal totalAportado) {
		this.totalAportado = totalAportado;
	}
	public List<Aportacionparticipante> getAportacionesParticipante() {
		return this.aportacionesParticipante;
	}

	public void setAportacionesParticipante(List<Aportacionparticipante> aportacionesParticipante) {
		this.aportacionesParticipante = aportacionesParticipante;
	}

	public Aportacionparticipante addAportacionesParticipante(Aportacionparticipante aportacionParticipante) {
		getAportacionesParticipante().add(aportacionParticipante);
		aportacionParticipante.setParticipanteRomeria(this); // Asegúrate que el campo de la FK en AportacionParticipante se llama 'participanteRomeria'
		return aportacionParticipante;
	}

	public Aportacionparticipante removeAportacionesParticipante(Aportacionparticipante aportacionParticipante) {
		getAportacionesParticipante().remove(aportacionParticipante);
		aportacionParticipante.setParticipanteRomeria(null);
		return aportacionParticipante;
	}

}