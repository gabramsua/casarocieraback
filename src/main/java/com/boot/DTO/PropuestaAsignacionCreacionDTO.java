package com.boot.DTO;

public class PropuestaAsignacionCreacionDTO {
	private int idHabitacion;

//    @NotBlank(message = "El nombre de la persona no puede estar vacío")
    private String nombrePersona;

    // Constructor(es)
    public PropuestaAsignacionCreacionDTO() {
    }

    public PropuestaAsignacionCreacionDTO(int idHabitacion, String nombrePersona) {
        this.idHabitacion = idHabitacion;
        this.nombrePersona = nombrePersona;
    }

    // Getters y Setters
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }
}
