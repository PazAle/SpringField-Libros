package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.excepcion.StockInsuficienteException;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;

import java.util.List;

public interface ServicioPedido {

    Pedido obtenerPedidoPorUsuario(Usuario usuario);

    void agregarLibro(Libro libro, Pedido pedido) throws StockInsuficienteException;

    void guardarPedido(Pedido pedidoActual);

    List<Libro> obtenerLibrosDelPedido(Pedido pedido);

    void eliminarLibro(Libro libro, Pedido pedidoActual);
    void actualizarLibro(Libro libro, Pedido pedido);
    Double calcularTotal(List<Libro> libros);
}
