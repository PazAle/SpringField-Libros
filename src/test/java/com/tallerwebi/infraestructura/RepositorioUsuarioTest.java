package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.usuario.RepositorioUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.DatosFormulario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })


public class RepositorioUsuarioTest {

    @Autowired
    private RepositorioUsuario repositorioUsuario;
    private Usuario usuario;

    @Transactional
    @Rollback
    @Test
    public void queSePuedaGuardarUnUsuario(){
        Usuario usuario = new Usuario();

        repositorioUsuario.guardar(usuario);

        assertNotNull(usuario.getId());
    }


    @Transactional
    @Rollback
    @Test
    public void queSePuedaBuscarUnUsuarioPorEmail(){

        String email = "ivan123@gmail.com";

        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setEmail(email);

        repositorioUsuario.guardar(usuarioEsperado);

        Usuario buscado = repositorioUsuario.buscar(email);

        assertThat(buscado, is(notNullValue()));

    }
    /*
    @Transactional
    @Rollback
    @Test
    public void queSePuedaActualizarDatosDePerfilDeUsuario(){
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();

        this.repositorioUsuario.actualizarPerfil(usuario1.getId(),datos1);

        assertEquals(usuario1.getNombre(),"Gabriel");

    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaActualizarContraseniaDeUsuario(){
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();

        this.repositorioUsuario.actualizarContrasenia(usuario1.getId(),datos1);

        assertEquals(usuario1.getNombre(),"Gabriel");

    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaActualizarEmailDeUsuario(){
        Usuario usuario1 = dadoQueSeCreaUnUsuario();
        DatosFormulario datos1 = dadoQueSeCreanDatosFormulario();

        this.repositorioUsuario.actualizarEmail(usuario1.getId(),datos1);

        assertEquals(usuario1.getNombre(),"Gabriel");

    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaEliminarUsuario(){
        Usuario usuario1 = dadoQueSeCreaUnUsuario();

        assertTrue(this.repositorioUsuario.eliminar(usuario1.getId()));
    }*/

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
