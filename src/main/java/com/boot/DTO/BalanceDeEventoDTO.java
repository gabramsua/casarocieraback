package com.boot.DTO;

import java.math.BigDecimal;
import java.util.Date;

import com.boot.model.Balance;
import com.boot.model.Categoria; // Asumo que Categoria sí la quieres
import com.boot.model.Year;      // Asumo que Year sí lo quieres

public class BalanceDeEventoDTO {

    private int id;
    private String concepto;
    private Date fecha;
    private BigDecimal importe;
    private Boolean isIngreso;
    private String urlTicket; // Si lo necesitas

    private Categoria categoria; // Puedes incluir la categoría completa si es pequeña
    private Year year; // Puedes incluir el Year completo si es pequeño
    private ParticipanteRomeriaSimpleDTO participanteromeria; // Referencia al DTO del participante

    // Constructor que toma la entidad Balance y la mapea
    public BalanceDeEventoDTO(Balance balance) {
        this.id = balance.getId();
        this.concepto = balance.getConcepto();
        this.fecha = balance.getFecha();
        this.importe = BigDecimal.valueOf(balance.getImporte());
        this.isIngreso = balance.getIsIngreso() != 0;
        this.urlTicket = balance.getUrlTicket();

        this.categoria = balance.getCategoria();
        this.year = balance.getYear();

        // Mapea solo las propiedades deseadas de Participanteromeria al DTO simple
        if (balance.getParticipanteromeria() != null) {
            this.participanteromeria = new ParticipanteRomeriaSimpleDTO(balance.getParticipanteromeria());
        }
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public Boolean getIsIngreso() { return isIngreso; }
    public void setIsIngreso(Boolean isIngreso) { this.isIngreso = isIngreso; }
    public String getUrlTicket() { return urlTicket; }
    public void setUrlTicket(String urlTicket) { this.urlTicket = urlTicket; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Year getYear() { return year; }
    public void setYear(Year year) { this.year = year; }
    public ParticipanteRomeriaSimpleDTO getParticipanteromeria() { return participanteromeria; }
    public void setParticipanteromeria(ParticipanteRomeriaSimpleDTO participanteromeria) { this.participanteromeria = participanteromeria; }
}
