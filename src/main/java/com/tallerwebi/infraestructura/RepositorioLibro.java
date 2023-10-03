package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.libro.Libro;

import java.util.List;
import java.util.Set;

public interface RepositorioLibro {
    Set<Libro> getLibros();

    Libro obtenerLibroPorId(Long id);

    void guardar(Libro libro);

    void modificar(Libro libro);

    boolean borrarLibro (Long id);

    List<Libro> obtenerLibroPorNombre(String nombre);

}
