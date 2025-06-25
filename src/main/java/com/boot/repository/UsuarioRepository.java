package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByNombre(String nombre);

}
