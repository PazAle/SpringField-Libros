package com.tallerwebi.dominio.compra;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;

import java.util.List;

public interface ServicioCompra {
    List<Libro> obtenerLibrosCompradosPorUsuario(Usuario usuario);
}
