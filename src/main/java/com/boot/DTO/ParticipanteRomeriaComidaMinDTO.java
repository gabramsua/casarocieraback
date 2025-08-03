package com.boot.DTO;

import com.boot.model.Participantecomida;

public class ParticipanteRomeriaComidaMinDTO {

    private Long id;
    private Long participanteRomeriaId; // Solo el ID del participante, no el objeto completo
    // ... otros campos relevantes de ParticipanteRomeriaComida que no causen circularidad

    // Constructor, Getters y Setters
    public ParticipanteRomeriaComidaMinDTO(Participantecomida comida) {
        this.id = (long) comida.getId();
        if (comida.getParticipanteromeria() != null) {
            this.participanteRomeriaId = (long) comida.getParticipanteromeria().getId();
        }
        // Mapea otros campos de ParticipanteRomeriaComida aquí si son necesarios
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParticipanteRomeriaId() {
		return participanteRomeriaId;
	}

	public void setParticipanteRomeriaId(Long participanteRomeriaId) {
		this.participanteRomeriaId = participanteRomeriaId;
	}
}
