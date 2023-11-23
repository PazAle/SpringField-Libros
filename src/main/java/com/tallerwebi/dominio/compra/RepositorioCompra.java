package com.tallerwebi.dominio.compra;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.dominio.usuario.Usuario;

import java.util.List;

public interface RepositorioCompra {
    Compra generarCompra(Pedido pedido, Usuario usuario);

    List<Libro> obtenerLibrosCompradosPorUsuario(Usuario usuario);
}
