package com.boot.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the balance database table.
 * 
 */
@Entity
@NamedQuery(name="Balance.findAll", query="SELECT b FROM Balance b")
public class Balance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String concepto;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private double importe;

	private byte isIngreso;

	private String urlTicket;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="idCategoria")
	private Categoria categoria;

	//bi-directional many-to-one association to Participanteromeria
	@ManyToOne
	@JoinColumn(name="idParticipanteRomeria")
	private Participanteromeria participanteromeria;

	public Balance() {
	}
	
	

	public Balance(String concepto, Date fecha, double importe, byte isIngreso, String urlTicket, Categoria categoria,
			Participanteromeria participanteromeria) {
		super();
		this.concepto = concepto;
		this.fecha = fecha;
		this.importe = importe;
		this.isIngreso = isIngreso;
		this.urlTicket = urlTicket;
		this.categoria = categoria;
		this.participanteromeria = participanteromeria;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getImporte() {
		return this.importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public byte getIsIngreso() {
		return this.isIngreso;
	}

	public void setIsIngreso(byte isIngreso) {
		this.isIngreso = isIngreso;
	}

	public String getUrlTicket() {
		return this.urlTicket;
	}

	public void setUrlTicket(String urlTicket) {
		this.urlTicket = urlTicket;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Participanteromeria getParticipanteromeria() {
		return this.participanteromeria;
	}

	public void setParticipanteromeria(Participanteromeria participanteromeria) {
		this.participanteromeria = participanteromeria;
	}

}