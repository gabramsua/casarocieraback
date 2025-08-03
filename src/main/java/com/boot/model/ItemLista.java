package com.boot.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name="item_lista")
public class ItemLista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;
    
    private int cantidad;

    private boolean comprado;

    @ManyToOne
    @JoinColumn(name="lista_compra_id")
    private ListaCompra listaCompra;

    // Puedes añadir una referencia al usuario si lo necesitas
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

	public ItemLista(int id, String nombre, int cantidad, boolean comprado, ListaCompra listaCompra, Usuario usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.comprado = comprado;
		this.listaCompra = listaCompra;
		this.usuario = usuario;
	}

	public ItemLista() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isComprado() {
		return comprado;
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}

	public ListaCompra getListaCompra() {
		return listaCompra;
	}

	public void setListaCompra(ListaCompra listaCompra) {
		this.listaCompra = listaCompra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

    // Getters, setters, y constructores
    
}