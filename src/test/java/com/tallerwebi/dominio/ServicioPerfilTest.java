package com.tallerwebi.dominio;

import com.tallerwebi.dominio.comentario.RepositorioComentario;
import com.tallerwebi.dominio.comentario.ServicioComentarioImpl;
import com.tallerwebi.dominio.usuario.RepositorioUsuario;
import com.tallerwebi.dominio.usuario.ServicioUsuario;
import com.tallerwebi.dominio.usuario.ServicioUsuarioImpl;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.presentacion.DatosFormulario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPerfilTest {

    private RepositorioUsuario repositorioUsuario;

    private ServicioUsuario servicioUsuario;

    @BeforeEach
    public void init(){
        this.repositorioUsuario = mock(RepositorioUsuario.class);
        this.servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);
    }

    @Test
    public void queSePuedaActualizarUnPerfil(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();
        when(this.repositorioUsuario.actualizarPerfil(usuario1.getId(),datos1)).thenReturn(true);
        //ejecucion
        Boolean resultado = this.servicioUsuario.actualizarPerfil(usuario1.getId(),datos1);
        //validacion
        assertTrue(resultado);
    }
    /*
    @Test
    public void queSePuedaActualizarUnaContrasenia(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();
        when(this.repositorioUsuario.actualizarContrasenia(usuario1.getId(),datos1)).thenReturn(true);
        when(this.repositorioUsuario.buscarUsuarioPorId(usuario1.getId())).thenReturn(usuario1);
        //ejecucion
        Boolean resultado = this.servicioUsuario.actualizarContrasenia(usuario1.getId(),datos1);
        //validacion
        assertTrue(resultado);
    }*/

    @Test
    public void queSePuedaActualizarUnEmail(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();
        when(this.repositorioUsuario.actualizarEmail(usuario1.getId(),datos1)).thenReturn(true);
        //ejecucion
        Boolean resultado = this.servicioUsuario.actualizarEmail(usuario1.getId(),datos1);
        //validacion
        assertTrue(resultado);
    }

    @Test
    public void queSePuedaEliminarUnUsuario(){
        //preparacion
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        when(this.repositorioUsuario.desactivar(usuario1.getId())).thenReturn(true);
        //ejecucion
        Boolean resultado = this.servicioUsuario.eliminarUsuario(usuario1.getId());
        //validacion
        assertTrue(resultado);
    }


    private Usuario dadoQueSeCreaUnUsuario(){
        Usuario usuario = new Usuario();
        usuario.setId(32L);
        usuario.setEmail("ivandp6880@gmail.com");
        usuario.setNombre("Ivan");
        usuario.setApellido("Gabriel");
        usuario.setPassword("1234");
        return usuario;
    }

    private DatosFormulario dadoQueSeCreanDatosFormulario(){
        DatosFormulario datosFormulario = new DatosFormulario();
        datosFormulario.setIdUsuario(32L);
        datosFormulario.setNombre("Gabriel");
        datosFormulario.setApellido("Gonzalez");
        datosFormulario.setNuevaClave("4321");
        datosFormulario.setConfirmarClave("4321");
        datosFormulario.setClaveActual("1234");
        return datosFormulario;
    }

}
