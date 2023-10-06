package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Transactional
    @Rollback
    @Test
    public void queSeModifiqueCorrectamenteElUsuario() {
    }



}
