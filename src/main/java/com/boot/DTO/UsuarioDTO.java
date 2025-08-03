package com.boot.DTO;

import com.boot.model.Usuario;

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private boolean isAdmin;
    private CasaDTO casa; // Usamos CasaDTO aquí

    public UsuarioDTO(Usuario usuario) {
        this.id = (long) usuario.getId();
        this.nombre = usuario.getNombre();
        this.isAdmin = (usuario.getIsAdmin() == 1); // Asumiendo que isAdmin es 1/0
        if (usuario.getCasa() != null) {
            this.casa = new CasaDTO(usuario.getCasa());
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public CasaDTO getCasa() {
		return casa;
	}

	public void setCasa(CasaDTO casa) {
		this.casa = casa;
	}
}
