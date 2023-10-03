package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorLibroTest {
    private ControladorLibro controladorLibro;
    private Libro libroMock;
    private DatosLibro datosLibroMock;
    private ServicioLibro servicioLibroMock;

    @BeforeEach
    public void init(){
        datosLibroMock = new DatosLibro();
        libroMock = mock(Libro.class);
        servicioLibroMock = mock(ServicioLibro.class);
        controladorLibro = new ControladorLibro(servicioLibroMock);
    }

    @Test
    public void queAlRegistrarUnLibroMeRedirijaAlHome() throws LibroExistente {
        ModelAndView modelAndView = this.controladorLibro.altaLibro(libroMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));

    }

    @Test
    public void queAlIntentarRegistrarUnLibroQueYaExisteMuestreErrorYVuelvaAlFormulario() throws LibroExistente {
        doThrow(LibroExistente.class).when(servicioLibroMock).registrarLibro(libroMock);

        ModelAndView modelAndView = this.controladorLibro.altaLibro(libroMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-libro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El libro ya existe"));
    }
}
