package com.tallerwebi.dominio;

import com.tallerwebi.dominio.categoria.Categoria;
import com.tallerwebi.dominio.categoria.ServicioCategoria;
import com.tallerwebi.dominio.categoria.ServicioCategoriaImpl;
import com.tallerwebi.infraestructura.RepositorioCategoria;
import com.tallerwebi.dominio.excepcion.CategoriaExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;

public class ServicioCategoriaTest {

    private ServicioCategoria servicioCategoria;
    private RepositorioCategoria repositorioCategoria;

    @BeforeEach
    public void init(){
        this.repositorioCategoria = mock(RepositorioCategoria.class);
        this.servicioCategoria = new ServicioCategoriaImpl(this.repositorioCategoria);
    }

    @Test
    public void cuandoObtengoLaCategoriaSeaLaBuscada(){

        Categoria categoriaBuscada = new Categoria();
        categoriaBuscada.setId(132);

        when(this.repositorioCategoria.buscarCategoria(categoriaBuscada.getId())).thenReturn(categoriaBuscada);

        Categoria categoriaObtenida = this.servicioCategoria.obtenerCategoria(categoriaBuscada.getId());

        assertThat(categoriaObtenida.getId(),is(132));

    }

    @Test
    public void registrarCorrectamenteUnaCategoriaInexistente(){

        Categoria categoriaBuscada = new Categoria();
        categoriaBuscada.setId(132);

        when(this.repositorioCategoria.buscarCategoria(categoriaBuscada.getId())).thenReturn(null);

        assertDoesNotThrow(() -> this.servicioCategoria.registrarCategoria(categoriaBuscada));

    }

    @Test
    public void registrarUnaCategoriaExistente(){

        Categoria categoriaNueva = new Categoria();
        categoriaNueva.setNombre("Drama");

        Categoria categoriaDuplicada = new Categoria();
        categoriaDuplicada.setNombre("Drama");

        when(this.repositorioCategoria.buscarCategoriaPorNombre(categoriaNueva.getNombre())).thenReturn(categoriaDuplicada);

        assertThrows(CategoriaExistente.class, () -> this.servicioCategoria.registrarCategoria(categoriaNueva));

    }


}
