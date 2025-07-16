package com.boot.DTO;

import java.util.List;

public class DetallePropuestaDTO {

    private List<String> votosAFavor;
    private List<String> votosEnContra;
    private List<AsignacionAgrupadaDTO> asignaciones;
    

    public DetallePropuestaDTO(List<String> votosAFavor, List<String> votosEnContra, List<AsignacionAgrupadaDTO> asignaciones) {
        this.votosAFavor = votosAFavor;
        this.votosEnContra = votosEnContra;
        this.asignaciones = asignaciones;
    }
    
	public List<String> getVotosAFavor() {
		return votosAFavor;
	}
	public void setVotosAFavor(List<String> votosAFavor) {
		this.votosAFavor = votosAFavor;
	}
	public List<String> getVotosEnContra() {
		return votosEnContra;
	}
	public void setVotosEnContra(List<String> votosEnContra) {
		this.votosEnContra = votosEnContra;
	}
	public List<AsignacionAgrupadaDTO> getAsignaciones() {
		return asignaciones;
	}
	public void setAsignaciones(List<AsignacionAgrupadaDTO> asignaciones) {
		this.asignaciones = asignaciones;
	}
}
