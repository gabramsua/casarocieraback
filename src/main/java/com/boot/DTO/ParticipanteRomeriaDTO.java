package com.boot.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.boot.model.Participanteromeria;

public class ParticipanteRomeriaDTO {

    private Long id;
    private UsuarioDTO usuario; // Usamos un DTO para Usuario
    private YearDTO year;       // Usamos un DTO para Year
    private BigDecimal costo;
    private BigDecimal totalAportado;
    private List<BalanceDTO> balances; // Lista de DTOs de Balance
    private List<ParticipanteRomeriaComidaMinDTO> participanteromeriacomidas; // ¡Aquí rompemos la circularidad!

    // Constructor, Getters y Setters
    public ParticipanteRomeriaDTO(Participanteromeria participanteRomeria) {
        this.id = (long) participanteRomeria.getId();
        this.costo = participanteRomeria.getCosto();
        this.totalAportado = participanteRomeria.getTotalAportado();

        if (participanteRomeria.getUsuario() != null) {
            this.usuario = new UsuarioDTO(participanteRomeria.getUsuario());
        }
        if (participanteRomeria.getYear() != null) {
            this.year = new YearDTO(participanteRomeria.getYear());
        }

        if (participanteRomeria.getBalances() != null) {
            this.balances = participanteRomeria.getBalances().stream()
                .map(BalanceDTO::new)
                .collect(Collectors.toList());
        }

        if (participanteRomeria.getParticipanteromeriacomidas() != null) {
            this.participanteromeriacomidas = participanteRomeria.getParticipanteromeriacomidas().stream()
                .map(ParticipanteRomeriaComidaMinDTO::new) // Usamos el DTO mínimo aquí
                .collect(Collectors.toList());
        }
    }

}
