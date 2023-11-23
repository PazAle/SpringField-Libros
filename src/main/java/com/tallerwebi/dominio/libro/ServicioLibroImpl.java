package com.tallerwebi.dominio.libro;

import com.tallerwebi.dominio.calificacion.Calificacion;
import com.tallerwebi.dominio.excepcion.LibroInexistente;
import com.tallerwebi.dominio.imagen.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.*;
import java.util.List;

@Service("servicioLibro")
@Transactional
public class ServicioLibroImpl implements ServicioLibro {

    private RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioLibroImpl(RepositorioLibro repositorioLibro){
        this.repositorioLibro = repositorioLibro;
    }
    @Override
    public List<Libro> getLibros() {
        return repositorioLibro.getLibros();
    }

    @Override
    public Libro obtenerLibro(Long id) {
        return repositorioLibro.obtenerLibroPorId(id);
    }

    public List <Libro> obtenerLibroPorNombre(String nombre){
        return repositorioLibro.obtenerLibroPorNombre(nombre);
    }

    @Override
    public List<Libro> busqueda(String termino) {
        return repositorioLibro.obtenerLibrosPorTermino(termino);
    }

      @Override
    public void calificarLibro(Long idLibro, Long idUsuario, Integer valor) {

       Calificacion calificacionObtenida = repositorioLibro.buscarCalificacion(idLibro, idUsuario);

       if (calificacionObtenida == null ){
            repositorioLibro.calificarLibro(idLibro, idUsuario, valor);
       }else {
           calificacionObtenida.setValoracion(valor);
            repositorioLibro.actualizarCalificacion(calificacionObtenida);
       }
    }

    @Override
    public Calificacion buscarCalificacion(Long libroId, Long idUsuario) {
        return repositorioLibro.buscarCalificacion(libroId, idUsuario);
    }

    @Override
    public List<Calificacion> buscarCalificaciones(Long libroId) {
        return repositorioLibro.obtenerCalificacionesPorLibro(libroId);
    }

    @Override
    public Integer obtenerPromedioCalificacionesPorLibro(Long libroId) {
        List<Calificacion> calificaciones = buscarCalificaciones(libroId);

        Integer cantCalif = calificaciones.size();
        Integer sumaDeValores = 0;

        for (Calificacion calificacion:calificaciones) {
            sumaDeValores += calificacion.getValoracion();
        }
        if(cantCalif == null  || cantCalif == 0){

            return 0;
        }

        Double promedio = (double) (sumaDeValores/cantCalif);



        return (int)(promedio+0.5);
    }



    /*@Override
    public void modificarLibro(Libro libro) throws LibroInexistente {
        Libro libroInexistente = this.repositorioLibro.obtenerLibroPorId(libro.getID());
        if(libroInexistente == null){
            throw new LibroInexistente();
        }
        this.repositorioLibro.modificar(libro);
    }*/



}
