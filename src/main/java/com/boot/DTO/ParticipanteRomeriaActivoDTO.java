package com.boot.DTO;

import java.math.BigDecimal;

import com.boot.model.Participanteromeria;

public class ParticipanteRomeriaActivoDTO {
    private Long id;
    private UsuarioDTO usuario; // Sigue necesitando el DTO de Usuario
    private YearDTO year;       // Sigue necesitando el DTO de Year
    private BigDecimal costo;
    private BigDecimal totalAportado;
    // ¡NO incluimos List<BalanceDTO> balances;
    // ¡NI List<ParticipanteRomeriaComidaMinDTO> participanteromeriacomidas;

    // Constructor que toma la entidad y solo mapea lo que necesitas
    public ParticipanteRomeriaActivoDTO(Participanteromeria participanteRomeria) {
        this.id = (long) participanteRomeria.getId();
        this.costo = participanteRomeria.getCosto();
        this.totalAportado = participanteRomeria.getTotalAportado();

        if (participanteRomeria.getUsuario() != null) {
            this.usuario = new UsuarioDTO(participanteRomeria.getUsuario());
        }
        if (participanteRomeria.getYear() != null) {
            this.year = new YearDTO(participanteRomeria.getYear());
        }
        // No se mapean las colecciones 'balances' ni 'participanteromeriacomidas'
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public YearDTO getYear() {
		return year;
	}

	public void setYear(YearDTO year) {
		this.year = year;
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

}
