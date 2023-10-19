package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;

public interface ServicioPedido {

    Pedido obtenerPedidoPorUsuario(Usuario usuario);

    void agregarLibro(Libro libro, Usuario usuario);
}
