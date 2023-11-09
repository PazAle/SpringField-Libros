package com.tallerwebi.dominio.libro;
import com.tallerwebi.dominio.calificacion.Calificacion;
import com.tallerwebi.dominio.excepcion.LibroInexistente;
import com.tallerwebi.dominio.imagen.Imagen;

import java.util.List;

public interface ServicioLibro {

    List<Libro> getLibros();

    Libro obtenerLibro(Long id);

    List<Libro> obtenerLibroPorNombre(String nombre);

    List<Libro> busqueda(String termino);

    void calificarLibro(Long idLibro, Long idUsuario, Integer valor);

    Calificacion buscarCalificacion(Long libroId, Long idUsuario);

    List<Calificacion> buscarCalificaciones(Long libroId);

    Integer obtenerPromedioCalificacionesPorLibro(Long libroId);


   //List<Imagen> getImagenesSecundarias();

    //List<Imagen> filtrarImagenesMetodosPago(List<Imagen> imagenesTotalesObtenidas);

   // Imagen ObtenerImagenLogo(List<Imagen> imagenesTotalesObtenidas);

   // List<Imagen> filtrarImagenesCarrusel(List<Imagen> imagenesTotalesObtenidas);

    //void modificarLibro(Libro libro);

}
