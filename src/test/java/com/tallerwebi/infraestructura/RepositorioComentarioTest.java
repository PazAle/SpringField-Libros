/*package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.categoria.RepositorioCategoria;
import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.RepositorioComentario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })
public class RepositorioComentarioTest {

    @Autowired
    private RepositorioComentario repositorioComentario;
    private static final Integer ID = 123;

    @Transactional
    @Rollback
    @Test
    public void queSePuedaGuardarUnComentario(){
        Comentario comentario = new Comentario();
        Boolean guardado = this.repositorioComentario.guardar(comentario);
        assertTrue(guardado);
    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaObtenerUnComentarioPersistidoPorId(){
        //preparacion
        Comentario comentarioCreado = new Comentario();
        repositorioComentario.guardar(comentarioCreado);

        //ejecución
        Comentario comentarioObtenido = repositorioComentario.obtenerComentarioPorId(comentarioCreado.getId());

        //verificación
        assertEquals(comentarioCreado, comentarioObtenido);
    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaBorrarUnComentario(){
        Comentario comentarioCreado = new Comentario();
        repositorioComentario.guardar(comentarioCreado);

        Boolean borrado = repositorioComentario.borrar(comentarioCreado);
        Comentario comentarioObtenido = repositorioComentario.obtenerComentarioPorId(comentarioCreado.getId());
        assertTrue(borrado);
        assertThat(comentarioObtenido, is(nullValue()));
    }


}
*/