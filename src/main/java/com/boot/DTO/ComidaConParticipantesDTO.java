package com.boot.DTO;

import java.util.List;

import com.boot.model.Participantecomida;

public class ComidaConParticipantesDTO {
    private int id;
    private TurnoComidaDTO turnoComida;
    private EventoMenuDTO year;
    private List<UsuarioDTO> participantes;

    public ComidaConParticipantesDTO(Participantecomida comidaEntity, List<UsuarioDTO> participantes) {
        this.id = comidaEntity.getId();
        this.turnoComida = new TurnoComidaDTO(comidaEntity.getTurnocomida());
        this.year = new EventoMenuDTO(comidaEntity.getParticipanteromeria().getYear());
        this.participantes = participantes;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public TurnoComidaDTO getTurnoComida() { return turnoComida; }
    public void setTurnoComida(TurnoComidaDTO turnoComida) { this.turnoComida = turnoComida; }

    public EventoMenuDTO getYear() { return year; }
    public void setYear(EventoMenuDTO year) { this.year = year; }

    public List<UsuarioDTO> getParticipantes() { return participantes; }
    public void setParticipantes(List<UsuarioDTO> participantes) { this.participantes = participantes; }
}