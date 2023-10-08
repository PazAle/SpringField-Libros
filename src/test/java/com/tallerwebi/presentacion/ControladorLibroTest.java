package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;


public class ControladorLibroTest {

    private Libro libroMock;
    private ServicioLibro servicioLibroMock;
    private ControladorLibro controladorLibro;

    @BeforeEach
    public void init(){
        servicioLibroMock = mock(ServicioLibro.class);
        this.controladorLibro = new ControladorLibro(servicioLibroMock);
    }

    @Test
    public void quelaBusquedaDevuelvaLaVistaResultadosDeBusqueda() {

        DatosLibro datosLibro = dadoQueSeCreaDatosDeUnLibro();

        List<Libro> librosResultadoMock = new ArrayList<>();

        when(servicioLibroMock.obtenerLibroPorNombre(anyString())).thenReturn(librosResultadoMock);
        ModelAndView modelAndView = this.controladorLibro.buscarLibros(datosLibro);
        assertThat(modelAndView.getViewName(), is("resultado_busqueda"));
    }

    @Test
    public void quelaBusquedaDevuelvaUnaListaNoVacia() {

        DatosLibro datosLibro = dadoQueSeCreaDatosDeUnLibro();
        Libro libroEncontrado = dadoQueSeCreaUnLibro();


        List<Libro> librosResultadoMock = new ArrayList<>();
        librosResultadoMock.add(libroEncontrado);


        when(servicioLibroMock.obtenerLibroPorNombre(anyString())).thenReturn(librosResultadoMock);
        ModelAndView modelAndView = this.controladorLibro.buscarLibros(datosLibro);
        assertThat(modelAndView.getModel().get("librosResultado"), is(notNullValue()));

    }

    @Test
    public void quelaBusquedaDeUnLibroQueNoExisteIndiqueMensaje() {

        DatosLibro datosLibro = dadoQueSeCreaDatosDeUnLibro();

        List<Libro> librosResultadoMock = new ArrayList<>();

        when(servicioLibroMock.obtenerLibroPorNombre(anyString())).thenReturn(librosResultadoMock);
        ModelAndView modelAndView = this.controladorLibro.buscarLibros(datosLibro);
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No se encontraron libros con ese nombre."));

    }

    private DatosLibro dadoQueSeCreaDatosDeUnLibro(){
        DatosLibro datosLibro = new DatosLibro();
        datosLibro.setNombre("NombrePrueba");
        return datosLibro;
    }

    private Libro dadoQueSeCreaUnLibro(){
        Libro libro = new Libro();
        return libro;
    }
}
