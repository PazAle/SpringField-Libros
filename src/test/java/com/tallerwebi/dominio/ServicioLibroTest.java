package com.tallerwebi.dominio;


import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import com.tallerwebi.dominio.libro.ServicioLibroImpl;
import com.tallerwebi.infraestructura.RepositorioLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
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

        Libro libro = new Libro();
        libro.setID(3L);
        libro.setNombre("Rock and Roll");

        Libro libro2 = new Libro();
        libro2.setID(5L);
        libro2.setNombre("Un poco de amor frances");

        Libro libro3 = new Libro();
        libro3.setID(8L);
        libro3.setNombre("Dr. Saturno");

        Set <Libro> listaLibros = new HashSet<>();
        listaLibros.add(libro);
        listaLibros.add(libro2);
        listaLibros.add(libro3);

        when(this.repositorioLibro.getLibros()).thenReturn(listaLibros);

        Set<Libro> librosObtenidos = this.servicioLibro.getLibros();

        assertThat(librosObtenidos, not(empty()));
    }

    @Test
    public void queSePuedaObtenerUnaListaDeLibrosQueDevuelvaTresLibros(){

        Libro libro = new Libro();
        libro.setID(3L);
        libro.setNombre("Rock and Roll");

        Libro libro2 = new Libro();
        libro2.setID(5L);
        libro2.setNombre("Un poco de amor frances");

        Libro libro3 = new Libro();
        libro3.setID(8L);
        libro3.setNombre("Dr. Saturno");

        Set <Libro> listaLibros = new HashSet<>();
        listaLibros.add(libro);
        listaLibros.add(libro2);
        listaLibros.add(libro3);

        when(this.repositorioLibro.getLibros()).thenReturn(listaLibros);

        Set<Libro> librosObtenidos = this.servicioLibro.getLibros();

        assertThat(librosObtenidos.size(), is(3));
    }

    @Test
    public void queSePuedaObtenerUnLibroPorSuId(){

        Libro libro = new Libro();
        libro.setID(3L);
        libro.setNombre("Rock and Roll");

        when(this.repositorioLibro.obtenerLibroPorId(ID)).thenReturn(libro);

        Libro libroObtenido = this.servicioLibro.obtenerLibro(ID);

        assertThat(libroObtenido.getID(), is(ID));
    }

    @Test
    public void queSePuedaObtenerUnLibroPorNombre(){

        List<Libro> listaCompletaLibros = new ArrayList<>();
        Libro libro1 = new Libro();
        libro1.setID(9L);
        libro1.setNombre("Un poco de amor frances");
        listaCompletaLibros.add(libro1);

        Libro libro2 = new Libro();
        libro2.setID(15L);
        libro2.setNombre("Un poco de amor frances");
        listaCompletaLibros.add(libro2);

        Libro libro3 = new Libro();
        libro3.setID(57L);
        libro3.setNombre("Un poco de amor frances");
        listaCompletaLibros.add(libro3);

        when(this.repositorioLibro.obtenerLibroPorNombre("Un poco de amor frances")).thenReturn((List<Libro>) listaCompletaLibros);

        List<Libro> librosObtenidos = this.servicioLibro.obtenerLibroPorNombre("Un poco de amor frances");

        assertEquals(listaCompletaLibros,librosObtenidos);

    }

    @Test
    public void queSePuedaDarDeBajaUnLibro(){

        when(this.repositorioLibro.borrarLibro(ID)).thenReturn(true);
        //
        assertTrue(this.servicioLibro.eliminarLibro(ID));
    }

}
