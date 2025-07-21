package com.boot.model;

import java.io.Serializable;
import java.math.BigDecimal; // Para el tipo de dato DECIMAL en importe
import java.util.Date; // Para el tipo de dato Date

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Si la necesitas

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * The persistent class for the aportacionparticipante database table.
 * */
@Entity
@Table(name="aportacionparticipante") // Asegúrate de que el nombre de la tabla coincide exactamente con el de la BD
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ajusta esto si manejas otras propiedades ignoradas
@NamedQuery(name="Aportacionparticipante.findAll", query="SELECT a FROM Aportacionparticipante a")
public class Aportacionparticipante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="importe")
	private BigDecimal importe; // Usamos BigDecimal para mapear DECIMAL(10,2)

	@Temporal(TemporalType.DATE) // Para mapear el tipo DATE de MySQL
	private Date fecha;

	private String concepto;

	//bi-directional many-to-one association to Participanteromeria
	@ManyToOne
	@JoinColumn(name="idParticipanteRomeria") // El nombre de la columna FK en la DB
	private Participanteromeria participanteRomeria; // Nombre del campo en la entidad Java (camelCase)

	public Aportacionparticipante() {
	}

	// --- Getters y Setters ---

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Participanteromeria getParticipanteRomeria() {
		return this.participanteRomeria;
	}

	public void setParticipanteRomeria(Participanteromeria participanteRomeria) {
		this.participanteRomeria = participanteRomeria;
	}
}