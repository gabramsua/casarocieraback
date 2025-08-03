package com.boot.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.boot.model.Balance;

public class BalanceDTO {

    private Long id;
    private String concepto;
    private Date fecha;
    private double importe;
    private Integer isIngreso;
    private String urlTicket;
    private CategoriaDTO categoria; // DTO para Categoria
    private YearDTO year;           // DTO para Year
    private Long participanteRomeriaId; // Referencia solo por ID para evitar circularidad

    // Constructor, Getters y Setters
    public BalanceDTO(Balance balance) {
        this.id = (long) balance.getId();
        this.concepto = balance.getConcepto();
        this.fecha = balance.getFecha();
        this.importe = balance.getImporte();
        this.isIngreso = (int) balance.getIsIngreso();
        this.urlTicket = balance.getUrlTicket();

        if (balance.getCategoria() != null) {
            this.categoria = new CategoriaDTO(balance.getCategoria());
        }
        if (balance.getYear() != null) {
            this.year = new YearDTO(balance.getYear());
        }
        if (balance.getParticipanteromeria() != null) {
            this.participanteRomeriaId = (long) balance.getParticipanteromeria().getId();
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public Integer getIsIngreso() {
		return isIngreso;
	}

	public void setIsIngreso(Integer isIngreso) {
		this.isIngreso = isIngreso;
	}

	public String getUrlTicket() {
		return urlTicket;
	}

	public void setUrlTicket(String urlTicket) {
		this.urlTicket = urlTicket;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public YearDTO getYear() {
		return year;
	}

	public void setYear(YearDTO year) {
		this.year = year;
	}

	public Long getParticipanteRomeriaId() {
		return participanteRomeriaId;
	}

	public void setParticipanteRomeriaId(Long participanteRomeriaId) {
		this.participanteRomeriaId = participanteRomeriaId;
	}
}
