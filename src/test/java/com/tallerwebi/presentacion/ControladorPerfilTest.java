package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.libro.ServicioLibro;
import com.tallerwebi.dominio.usuario.ServicioUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPerfilTest {

    private ServicioUsuario servicioUsuarioMock;
    private ServicioImagen servicioImagenMock;
    private HttpSession sessionMock;
    private ControladorPerfil controladorPerfil;

    @BeforeEach
    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioImagenMock = mock(ServicioImagen.class);
        sessionMock = mock(HttpSession.class);
        this.controladorPerfil = new ControladorPerfil(servicioUsuarioMock, servicioImagenMock);
    }

    @Test
    public void queSeAccedaAlPerfil(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        //ejecución
        ModelAndView modelAndView = this.controladorPerfil.irAPerfil(sessionMock);
        //validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
    }

    @Test
    public void queSeObtenganLosDatosDelUsuarioConExito(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        when(servicioUsuarioMock.obtenerDatosDeUsuario(anyLong())).thenReturn(usuario1);
        //ejecución
        ResponseEntity<Usuario> respuestaTest = this.controladorPerfil.obtenerDatosParaAjuste(usuario1.getId());
        //validacion
        assertEquals(HttpStatus.OK, respuestaTest.getStatusCode());
    }

    @Test
    public void queSeActualiceLosDatosDelUsuario(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();
        when(servicioUsuarioMock.actualizarPerfil(usuario1.getId(),datos1)).thenReturn(true);
        //ejecucion
        ResponseEntity<Boolean> respuestaTest = this.controladorPerfil.actualizarPerfil(datos1);
        //validacion
        assertEquals(HttpStatus.OK, respuestaTest.getStatusCode());
    }

    @Test
    public void queSeElimineElUsuario(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        when(servicioUsuarioMock.eliminarUsuario(usuario1.getId())).thenReturn(true);
        //ejecucion
        ResponseEntity<String> respuestaTest = this.controladorPerfil.eliminarUsuario(usuario1.getId(),sessionMock);
        //validacion
        assertEquals(HttpStatus.OK, respuestaTest.getStatusCode());
        assertEquals("{\"success\": true}", respuestaTest.getBody());
    }

    private Usuario dadoQueSeCreaUnUsuario(){
        Usuario usuario = new Usuario();
        usuario.setId(32L);
        usuario.setEmail("ivandp6880@gmail.com");
        usuario.setNombre("Ivan");
        usuario.setApellido("Gabriel");
        return usuario;
    }

    private DatosFormulario dadoQueSeCreanDatosFormulario(){
        DatosFormulario datosFormulario = new DatosFormulario();
        datosFormulario.setIdUsuario(32L);
        datosFormulario.setNombre("Gabriel");
        datosFormulario.setApellido("Gonzalez");
        return datosFormulario;
    }

}
