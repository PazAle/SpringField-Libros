package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;

import java.util.List;

public interface RepositorioPedido {

    Pedido obtenerPedidoPorUsuario(Usuario usuario);

    void guardarPedido(Pedido pedidoActual);

    void agregarLibro(Libro libro, Pedido pedido);


    List<Libro> obtenerLibrosDelPedido(Pedido pedido);

    void eliminarLibro(Libro libro, Pedido pedidoActual);

    void vaciarPedido(Pedido pedido);
}
