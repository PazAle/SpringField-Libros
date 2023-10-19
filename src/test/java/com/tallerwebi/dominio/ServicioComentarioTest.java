package com.tallerwebi.dominio;

import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.RepositorioComentario;
import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.comentario.ServicioComentarioImpl;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import com.tallerwebi.dominio.libro.ServicioLibroImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioComentarioTest {

    private RepositorioComentario repositorioComentario;
    private ServicioComentario servicioComentario;
    private static final Integer ID = 123;

    @BeforeEach
    public void init(){
        this.repositorioComentario = mock(RepositorioComentario.class);
        this.servicioComentario = new ServicioComentarioImpl(repositorioComentario);
    }

    @Test
    public void queSePuedaCrearUnComentario() {
        // Preparación
        //Comentario nuevoComentario = dadoQueCreoUnosComentarios(2).get(0);
       // nuevoComentario.setTexto("Este es un nuevo comentario");
        when(this.repositorioComentario.guardar(any(Comentario.class))).thenReturn(true);

        // Ejecución
       // boolean creadoExitosamente = this.servicioComentario.crearComentario(nuevoComentario);
        //boolean seCreoComentario = this.servicioComentario.crearComentario("Este es un nuevo comentario");
        Comentario comentario = new Comentario();
        comentario.setTexto("Hola soy un comentario");
        boolean seCreoComentario = this.servicioComentario.guardarComentario(comentario);
        // Verificación
        assertTrue(seCreoComentario);
    }

    @Test
    public void queSePuedaObtenerUnComentarioPorId(){
        //preparacion
        List<Comentario> comentario = dadoQueCreoUnosComentarios(1);
        comentario.get(0).setId(ID);
        when(this.repositorioComentario.obtenerComentarioPorId(ID)).thenReturn(comentario.get(0));
        //ejecución
        Comentario comentarioObtenido = this.servicioComentario.obtenerComentarioPorId(ID);

        //verificación
        assertThat(comentarioObtenido.getId(), is(ID));
    }

    @Test
    public void queSePuedaObtenerTodosLosComentariosDeUnLibro(){
        //preparación
        List<Comentario> comentariosCreados = dadoQueCreoUnosComentarios(5);
        Libro libro = new Libro();
        libro.setID(123L);
        //libro.setComentarios(comentariosCreados);
        when(this.repositorioComentario.getAllComentariosPorLibro(123L)).thenReturn(comentariosCreados);

        //ejecución
        List<Comentario> comentarioObtenidos = this.servicioComentario.obtenerTodosLosComentarios(123L);

        //verificación
        assertThat(comentarioObtenidos, hasSize(5));
    }

    @Test
    public void queNoSeObtengaUnComentarioPorIdNoGuardado(){
        //preparación
        List<Comentario> comentariosCreados = dadoQueCreoUnosComentarios(3);
        when(this.repositorioComentario.obtenerComentarioPorId(ID)).thenReturn(null);

        //ejecución
        Comentario comentarioObtenido = this.servicioComentario.obtenerComentarioPorId(ID);

        //verificación
        assertNull(comentarioObtenido);
    }

    private List<Comentario> dadoQueCreoUnosComentarios(Integer cantidad) {
        List<Comentario> comentariosPedidos = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            Comentario comentario = new Comentario();
            comentario.setId(i);
            comentario.setTexto("Texto de prueba " + i);
            comentariosPedidos.add(comentario);
        }

        return comentariosPedidos;
    }

}
