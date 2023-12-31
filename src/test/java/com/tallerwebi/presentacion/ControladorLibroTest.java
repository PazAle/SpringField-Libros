package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.imagen.ServicioImagen;
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
    private ServicioImagen servicioImagenMock;
    private ServicioComentario servicioComentarioMock;
    private ControladorLibro controladorLibro;


    private List<Libro> librosResultadoMock;

    @BeforeEach
    public void init(){
        servicioLibroMock = mock(ServicioLibro.class);
        servicioImagenMock = mock(ServicioImagen.class);
        servicioComentarioMock = mock(ServicioComentario.class);
        this.controladorLibro = new ControladorLibro(servicioLibroMock, servicioImagenMock, servicioComentarioMock);
        this.librosResultadoMock = new ArrayList<>();
    }
    @Test
    public void queCalificarLibroDevuelvaLavistaDetalleLibro(){
        //preparacion
        Long libroId = 1L;
        Integer valoracion = 5;

        //ejecucion
        ModelAndView modelAndView = dadoQueObtengoLaVistaDetalleLibro(libroId,valoracion);

        //validacion

        verificoCoincidenciaDeNombresDeVistadeCalificacion(modelAndView);


    }

    private void verificoCoincidenciaDeNombresDeVistadeCalificacion(ModelAndView modelAndView) {
        assertThat(modelAndView.getViewName(), is("redirect://detalle-libro?id="+1L));
    }
    private ModelAndView dadoQueObtengoLaVistaDetalleLibro(Long libroId, Integer valoracion) {

        return this.controladorLibro.calificarLibro(libroId,valoracion);
    }

    @Test
    public void quelaBusquedaDevuelvaLaVistaResultadosDeBusqueda() {

        //preparación
        DatosLibro datosLibro = dadoQueSeCreaDatosDeUnLibro();
        //when(servicioLibroMock.obtenerLibroPorNombre(anyString())).thenReturn(librosResultadoMock);
        mockeoLaObtencionDeLibrosPorNombre(servicioLibroMock);

        //ejecución
        ModelAndView modelAndView = dadoQueObtengoLaVistaResultadoBusqueda(datosLibro);

        //verificación
        verificoCoincidenciaDeNombresDeVistas(modelAndView);
        //assertThat(modelAndView.getViewName(), is("resultado_busqueda"));
    }

    @Test
    public void quelaBusquedaDevuelvaUnaListaNoVacia() {

        //preparación
        DatosLibro datosLibro = dadoQueSeCreaDatosDeUnLibro();
        Libro libroCreado = dadoQueSeCreaUnLibro();
        dadoQueAgregoUnLibroALibrosResultadosMock(libroCreado);
        mockeoLaBusqueda(servicioLibroMock);

        //ejecución
        ModelAndView modelAndView = dadoQueObtengoLaVistaResultadoBusqueda(datosLibro);

        //verificación
        verificoQueLaListaResultanteNoSeaNula(modelAndView);

    }

    @Test
    public void quelaBusquedaDeUnLibroQueNoExisteIndiqueMensaje() {

        //preparación
        DatosLibro datosLibro = dadoQueSeCreaDatosDeUnLibro();
        mockeoLaObtencionDeLibrosPorNombre(servicioLibroMock);

        //ejecución
        ModelAndView modelAndView = dadoQueObtengoLaVistaResultadoBusqueda(datosLibro);

        //verificación
        verificoMensajeDeErrorAlBuscarLibroNoExistente(modelAndView);

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

    private void mockeoLaObtencionDeLibrosPorNombre(ServicioLibro servicioLibroMock){
        when(servicioLibroMock.obtenerLibroPorNombre(anyString())).thenReturn(librosResultadoMock);
    }

    private void mockeoLaBusqueda(ServicioLibro servicioLibroMock){
        when(servicioLibroMock.busqueda(anyString())).thenReturn(librosResultadoMock);
    }

    private ModelAndView dadoQueObtengoLaVistaResultadoBusqueda(DatosLibro datosLibro){
        return this.controladorLibro.buscarLibros(datosLibro);
    }

    private void dadoQueAgregoUnLibroALibrosResultadosMock(Libro libroCreado){
        librosResultadoMock.add(libroCreado);
    }

    private void verificoCoincidenciaDeNombresDeVistas(ModelAndView modelAndView){
        assertThat(modelAndView.getViewName(), is("resultado_busqueda"));
    }

    private void verificoQueLaListaResultanteNoSeaNula(ModelAndView modelAndView){
        assertThat(modelAndView.getModel().get("librosResultado"), is(notNullValue()));
    }

    private void verificoMensajeDeErrorAlBuscarLibroNoExistente(ModelAndView modelAndView){
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No se encontraron libros con ese nombre."));
    }
}
