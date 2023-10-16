package com.tallerwebi.dominio.libro;
import com.tallerwebi.dominio.excepcion.LibroInexistente;
import com.tallerwebi.dominio.imagen.Imagen;

import java.util.List;

public interface ServicioLibro {

    List<Libro> getLibros();

    Libro obtenerLibro(Long id);

    List<Libro> obtenerLibroPorNombre(String nombre);

    List<Libro> busqueda(String termino);

   //List<Imagen> getImagenesSecundarias();

    //List<Imagen> filtrarImagenesMetodosPago(List<Imagen> imagenesTotalesObtenidas);

   // Imagen ObtenerImagenLogo(List<Imagen> imagenesTotalesObtenidas);

   // List<Imagen> filtrarImagenesCarrusel(List<Imagen> imagenesTotalesObtenidas);

    //void modificarLibro(Libro libro);

}
