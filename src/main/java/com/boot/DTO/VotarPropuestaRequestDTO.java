package com.boot.DTO;

public class VotarPropuestaRequestDTO {
	private int idPropuesta;
    private int idParticipanteromeria;
    private boolean isAFavor;

    // Getters y setters
    public int getIdPropuesta() {
        return idPropuesta;
    }
    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public int getIdParticipanteromeria() {
        return idParticipanteromeria;
    }
    public void setIdParticipanteromeria(int idParticipanteromeria) {
        this.idParticipanteromeria = idParticipanteromeria;
    }

    public boolean isIsAFavor() {
        return isAFavor;
    }
    public void setIsAFavor(boolean isAFavor) {
        this.isAFavor = isAFavor;
    }
}
