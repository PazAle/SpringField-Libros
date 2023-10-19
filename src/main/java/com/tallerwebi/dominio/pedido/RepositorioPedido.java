package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;

public interface RepositorioPedido {

    void agregarLibro(Libro libro, Usuario usuario);

    Pedido obtenerPedidoPorUsuario(Usuario usuario);
}
