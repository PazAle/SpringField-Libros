package com.tallerwebi.dominio.libro;

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



    /*@Override
    public void modificarLibro(Libro libro) throws LibroInexistente {
        Libro libroInexistente = this.repositorioLibro.obtenerLibroPorId(libro.getID());
        if(libroInexistente == null){
            throw new LibroInexistente();
        }
        this.repositorioLibro.modificar(libro);
    }*/



}
