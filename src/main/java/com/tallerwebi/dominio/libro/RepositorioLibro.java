package com.tallerwebi.dominio.libro;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.libro.Libro;

import java.util.List;
import java.util.Set;

public interface RepositorioLibro {
    List<Libro> getLibros();

    Libro obtenerLibroPorId(Long id);

    void guardar(Libro libro);

    void modificar(Libro libro);

    boolean borrarLibro (Long id);

    List<Libro> obtenerLibroPorNombre(String nombre);

    void actualizarLibro(Libro libro);

    List<Libro> obtenerLibrosPorTermino(String termino);


    //List<Imagen> obtenerImagenesSecundarias();
}
