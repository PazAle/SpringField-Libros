package com.tallerwebi.dominio.libro;
import com.tallerwebi.dominio.excepcion.LibroInexistente;

import java.util.List;

public interface ServicioLibro {

    List<Libro> getLibros();

    Libro obtenerLibro(Long id);

    List<Libro> obtenerLibroPorNombre(String nombre);

    //void modificarLibro(Libro libro);

}
