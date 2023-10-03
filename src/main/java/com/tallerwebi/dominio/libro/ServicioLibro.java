package com.tallerwebi.dominio.libro;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.LibroInexistente;

import java.util.List;
import java.util.Set;

public interface ServicioLibro {

    Set<Libro> getLibros();

    Libro obtenerLibro(Long id);

    List<Libro> obtenerLibroPorNombre(String nombre);

    void registrarLibro(Libro libro) throws LibroExistente;

    boolean eliminarLibro(Long id);

    void modificarLibro(Libro libro) throws LibroInexistente;


}
