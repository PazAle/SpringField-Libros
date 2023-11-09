package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.ldap.Control;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorComentarioTest {

    private ServicioLibro servicioLibroMock;
    private ServicioComentario servicioComentarioMock;
    private ControladorComentario controladorComentario;
    private static final Long ID = 123L;


    @BeforeEach
    public void init(){
        servicioLibroMock = mock(ServicioLibro.class);
        servicioComentarioMock = mock(ServicioComentario.class);
        this.controladorComentario = new ControladorComentario(servicioComentarioMock, servicioLibroMock);
    }

    @Test
    public void queSePuedaAgregarUnComentarioConExito(){
        //preparacion
        Libro libro = dadoQueSeCreaUnLibro();
        libro.setID(ID);
        //mockeoLaObtencionDeLibroCorrecta
        when(servicioLibroMock.obtenerLibro(anyLong())).thenReturn(libro);
        //mockeoGuardadoDeComentarioCorrecto
        when(servicioComentarioMock.guardarComentario(any(Comentario.class))).thenReturn(true);

        //ejecución
        ResponseEntity<String> respuestaTest = controladorComentario.agregarComentario(ID, "Texto de prueba");

        //validacion
        assertEquals(HttpStatus.OK, respuestaTest.getStatusCode());
        assertEquals("Comentario agregado exitosamente", respuestaTest.getBody());
    }

    @Test
    public void queNoSeAgregueUnComentarioPorLibroNoEncontrado() {
        // Preparación
        mockeoLaObtencionDeLibroNula();

        // Ejecución
        ResponseEntity<String> respuestaTest = controladorComentario.agregarComentario(1L, "Texto de prueba");

        // Verificación
        assertEquals(HttpStatus.NOT_FOUND, respuestaTest.getStatusCode());
        assertEquals("Libro no encontrado", respuestaTest.getBody());
    }

    @Test
    public void queAlAgregarUnComentarioNoSePuedaGuardar() {
        // Preparación
        Libro libro = dadoQueSeCreaUnLibro();
        mockeoLaObtencionDeLibroCorrecta(libro);
        mockeoGuardadoComentarioFalse();

        // Ejecución
        ResponseEntity<String> respuesta = controladorComentario.agregarComentario(1L, "Texto de prueba");

        // Verificación
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("No se pudo agregar el comentario", respuesta.getBody());
    }

    private Libro dadoQueSeCreaUnLibro(){
        Libro libro = new Libro();
        return libro;
    }

    private void mockeoLaObtencionDeLibroCorrecta(Libro libro){
        when(servicioLibroMock.obtenerLibro(anyLong())).thenReturn(libro);
    }

    private void mockeoGuardadoComentarioFalse(){
        when(servicioComentarioMock.guardarComentario(any(Comentario.class))).thenReturn(false);
    }

    private void mockeoLaObtencionDeLibroNula(){
        when(servicioLibroMock.obtenerLibro(anyLong())).thenReturn(null);
    }



}
