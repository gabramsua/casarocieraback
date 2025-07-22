package com.boot.DTO;

import java.math.BigDecimal;

import com.boot.model.Participanteromeria;
import com.boot.model.Usuario;

public class ParticipanteRomeriaSimpleDTO {

    private int id;
    private Usuario usuario; // Si quieres los detalles del usuario, si no, solo su ID
    private boolean isLogged;
    private BigDecimal costo;
    private BigDecimal totalAportado;

    // Constructor que toma la entidad Participanteromeria y la mapea
    public ParticipanteRomeriaSimpleDTO(Participanteromeria participante) {
        this.id = participante.getId();
        this.usuario = participante.getUsuario(); // O mapea solo usuario.getId(), usuario.getNombre()
//        this.isLogged = participante.getIsLogged();
        this.costo = participante.getCosto();
        this.totalAportado = participante.getTotalAportado();
        // NO INCLUIMOS COLECCIONES (balances, aportaciones, etc.)
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public boolean getIsLogged() { return isLogged; }
    public void setIsLogged(boolean isLogged) { this.isLogged = isLogged; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public BigDecimal getTotalAportado() { return totalAportado; }
    public void setTotalAportado(BigDecimal totalAportado) { this.totalAportado = totalAportado; }
}
