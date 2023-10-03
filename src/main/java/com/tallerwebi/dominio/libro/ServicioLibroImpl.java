package com.tallerwebi.dominio.libro;

import com.tallerwebi.dominio.categoria.Categoria;
import com.tallerwebi.dominio.excepcion.CategoriaInexistente;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.LibroInexistente;
import com.tallerwebi.infraestructura.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("servicioLibro")
public class ServicioLibroImpl implements ServicioLibro {

    private RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioLibroImpl(RepositorioLibro repositorioLibro){
        this.repositorioLibro = repositorioLibro;
    }
    @Override
    public Set<Libro> getLibros() {
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
    public void registrarLibro(Libro libro) throws LibroExistente {
        List <Libro> libroEncontrado = repositorioLibro.obtenerLibroPorNombre(libro.getNombre());
        if(libroEncontrado != null){
            throw new LibroExistente();
        }

        repositorioLibro.guardar(libro);
    }

    public boolean eliminarLibro(Long id){
        return repositorioLibro.borrarLibro(id);
    }

    @Override
    public void modificarLibro(Libro libro) throws LibroInexistente {
        Libro libroInexistente = this.repositorioLibro.obtenerLibroPorId(libro.getID());
        if(libroInexistente == null){
            throw new LibroInexistente();
        }
        this.repositorioLibro.modificar(libro);
    }


}
