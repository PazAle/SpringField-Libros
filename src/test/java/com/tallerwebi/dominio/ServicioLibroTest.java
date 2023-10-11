package com.tallerwebi.dominio;


import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import com.tallerwebi.dominio.libro.ServicioLibroImpl;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ServicioLibroTest {

    public static final Long ID = 3L;
    private ServicioLibro servicioLibro;
    private RepositorioLibro repositorioLibro;

    @BeforeEach
    public void init(){
        this.repositorioLibro = mock(RepositorioLibro.class);
        this.servicioLibro = new ServicioLibroImpl(repositorioLibro);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeLibrosQueNoEsteVacia(){

        //preparacion
        List <Libro> listaLibros = dadoQueCreoUnosLibros(3);

        mockeoUnaListaDeLibros(this.repositorioLibro, listaLibros);

        //ejecución
        List<Libro> librosObtenidos = dadoQueObtengoUnaListaDeLibrosDesdeServicio();

        //verificación
        verificarCondicionDeLista(librosObtenidos, libros -> !libros.isEmpty());
    }

    @Test
    public void queSePuedaObtenerUnaListaDeLibrosQueDevuelvaTresLibros(){

        //preparación
        List<Libro> librosCreados = dadoQueCreoUnosLibros(3);
        mockeoUnaListaDeLibros(this.repositorioLibro, librosCreados);

        //ejecución
        List<Libro> librosObtenidos = dadoQueObtengoUnaListaDeLibrosDesdeServicio();

        //verificación

        verificarCondicionDeLista(librosObtenidos, libros -> libros.size() == 3);

        //assertThat(librosObtenidos.size(), is(3));
    }

    @Test
    public void queSePuedaObtenerUnLibroPorSuId(){

        //preparación
        Libro libroCreado = dadoQueCreoUnosLibros(1).get(0);
        libroCreado.setID(ID);

        mockeoUnLibroPorID(this.repositorioLibro, libroCreado);

        //ejecución
        Libro libroObtenido = dadoQueObtengoUnLibroPorIDDesdeElServicio(ID);

        //validación
        verificoCoincidenciaEnLosID(libroObtenido.getID(), ID);
    }

    @Test
    public void queSePuedanObtenerTodosLosLibrosConElMismoNombre(){
        //preparación
        List<Libro> listaDeLibros = dadoQueCreoUnosLibros(20);
        String nombreABuscar = "Harry Potter";
        listaDeLibros.get(0).setNombre(nombreABuscar);
        listaDeLibros.get(5).setNombre(nombreABuscar);
        listaDeLibros.get(10).setNombre(nombreABuscar);

        mockeoUnaListaDeLibrosPorNombre(this.repositorioLibro, nombreABuscar, listaDeLibros);

        //ejecución
        List<Libro> librosObtenidos = dadoQueObtengoUnaListaDeLibrosDesdeServicioPorNombre(nombreABuscar);

        //validación
        for (Libro libro: librosObtenidos){
            assertEquals(nombreABuscar, libro.getNombre());
        }
    }

    private List<Libro> dadoQueCreoUnosLibros(Integer cantidad) {
        List<Libro> librosPedidos = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            Libro libro = new Libro();
            libro.setID((long) i);
            libro.setNombre("Libro " + i);
            librosPedidos.add(libro);
        }

        return librosPedidos;
    }

    private void mockeoUnaListaDeLibros(RepositorioLibro repositorioLibro, List<Libro> listaLibros){
        when(repositorioLibro.getLibros()).thenReturn(listaLibros);
        //return listaLibros;
    }

    private void mockeoUnLibroPorID(RepositorioLibro repositorioLibro, Libro libroObtenido){
        when(repositorioLibro.obtenerLibroPorId(ID)).thenReturn(libroObtenido);
    };

    private void mockeoUnaListaDeLibrosPorNombre(RepositorioLibro repositorioLibro, String nombre, List<Libro> listaLibros){
        when(repositorioLibro.obtenerLibroPorNombre(nombre)).thenReturn(listaLibros);
    }


    private List<Libro> dadoQueObtengoUnaListaDeLibrosDesdeServicio(){
      return this.servicioLibro.getLibros();
    };

    private List<Libro> dadoQueObtengoUnaListaDeLibrosDesdeServicioPorNombre(String nombre) {
        List<Libro> listaCompleta = this.servicioLibro.getLibros();
        List<Libro> listaFiltrada = new ArrayList<>();

        for (Libro libro : listaCompleta) {
            if (libro.getNombre().equals(nombre)) {
                listaFiltrada.add(libro);
            }
        }

        return listaFiltrada;
    }

    private Libro dadoQueObtengoUnLibroPorIDDesdeElServicio(Long id){
      return this.servicioLibro.obtenerLibro(id);
    };

   // private void validoQueLaListaNoEsteVacía(List<Libro> librosObtenidos) {
      //  assertThat(librosObtenidos, not(empty()));
   // }


    private void verificarCondicionDeLista(List<Libro> libros, Predicate<List<Libro>> condition) {
        assertTrue(condition.test(libros));
    }

    private void verificoCoincidenciaEnLosID(Long idObtenido, Long idBuscado){
        assertThat(idObtenido, is(idBuscado));
    }



}
