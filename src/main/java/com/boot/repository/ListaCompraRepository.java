package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.ListaCompra;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {
    // Aquí puedes añadir métodos de consulta personalizados si los necesitas
    List<ListaCompra> findAllByCasaId(int casaId);
}