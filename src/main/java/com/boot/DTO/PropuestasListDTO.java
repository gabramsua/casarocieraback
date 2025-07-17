package com.boot.DTO;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class PropuestasListDTO {
    private int id;
    private String autor;
    private Date fecha;
    private List<String> votosAFavor; // << CAMBIO AQUÍ: antes votosAFavorNombres
    private List<String> votosEnContra; // << CAMBIO AQUÍ: antes votosEnContraNombres

    // Constructor que usa la JPQL para la primera parte de la consulta
    public PropuestasListDTO(int id, String autor, Date fecha) {
        this.id = id;
        this.autor = autor;
        this.fecha = fecha;
        this.votosAFavor = new ArrayList<>(); // Inicializa para evitar NullPointer
        this.votosEnContra = new ArrayList<>(); // Inicializa para evitar NullPointer
    }

    // << CONSTRUCTOR ACTUALIZADO (si lo usas para construir el DTO completo de una vez) >>
    // Refleja los nuevos nombres de los campos
    public PropuestasListDTO(int id, String autor, Date fecha, List<String> votosAFavor, List<String> votosEnContra) {
        this.id = id;
        this.autor = autor;
        this.fecha = fecha;
        this.votosAFavor = votosAFavor;
        this.votosEnContra = votosEnContra;
    }


    // Getters y Setters (Asegúrate de que todos están presentes y reflejan los nuevos nombres)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<String> getVotosAFavor() { // << CAMBIO AQUÍ
        return votosAFavor;
    }

    public void setVotosAFavor(List<String> votosAFavor) { // << CAMBIO AQUÍ
        this.votosAFavor = votosAFavor;
    }

    public List<String> getVotosEnContra() { // << CAMBIO AQUÍ
        return votosEnContra;
    }

    public void setVotosEnContra(List<String> votosEnContra) { // << CAMBIO AQUÍ
        this.votosEnContra = votosEnContra;
    }
     
}
