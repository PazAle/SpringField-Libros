package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.categoria.Categoria;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.NotNull;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })

public class RepositorioCategoriaTest {

    @Autowired
    private RepositorioCategoria repositorioCategoria;


    @Transactional
    @Rollback
    @Test
    public void queSeObtengaLaCategoriaBuscada(){

        Categoria categoriaExistente = new Categoria();
        repositorioCategoria.alta(categoriaExistente);

        Categoria categoriaObtenida = repositorioCategoria.buscarCategoria(categoriaExistente.getId());

        assertEquals(categoriaExistente,categoriaObtenida);

    }

    @Transactional
    @Rollback
    @Test
    public void queSeObtengaLaCategoriaBuscadaPorNombre(){
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setNombre("Drama");
        repositorioCategoria.alta(categoriaExistente);

        Categoria categoriaObtenida = repositorioCategoria.buscarCategoriaPorNombre(categoriaExistente.getNombre());

        assertThat(categoriaObtenida.getNombre(), is("Drama"));
    }

    @Transactional
    @Rollback
    @Test
    public void darDeAltaUnaCategoriaDeberiaPersistirla(){
        Categoria categoriaNueva = new Categoria();
        repositorioCategoria.alta(categoriaNueva);

        Categoria categoriaEncontrada = repositorioCategoria.buscarCategoria(categoriaNueva.getId());

        assertThat(categoriaEncontrada, is(notNullValue()));

    }

    @Transactional
    @Rollback
    @Test
    public void darDeBajaUnaCategoria(){
        Categoria categoriaNueva = new Categoria();
        categoriaNueva.setNombre("Drama");
        repositorioCategoria.alta(categoriaNueva);

        repositorioCategoria.borrar(categoriaNueva);

        Categoria categoriaEncontrada = repositorioCategoria.buscarCategoria(categoriaNueva.getId());

        assertThat(categoriaEncontrada,is(nullValue()));

    }

    @Transactional
    @Rollback
    @Test
    public void modificarUnaCategoria(){
        Categoria categoriaNueva = new Categoria();
        categoriaNueva.setNombre("Drama");
        repositorioCategoria.alta(categoriaNueva);

        categoriaNueva.setNombre("Policial");

        repositorioCategoria.modificar(categoriaNueva);

        Categoria categoriaEncontrada = repositorioCategoria.buscarCategoria(categoriaNueva.getId());

        assertThat(categoriaEncontrada.getNombre(), is("Policial"));

    }

}
