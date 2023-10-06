package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.libro.Libro;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })

public class RepositorioLibroTest {

    @Autowired
    private RepositorioLibro repositorioLibro;
    private Libro libro;
    private String nombre = "Recuerdos que mienten un poco";

    @BeforeEach
    public void init(){
        libro = new Libro();
        libro.setNombre(nombre);
        repositorioLibro.guardar(libro);
    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaGuardarYPersistirUnLibro(){

        Libro buscado = repositorioLibro.obtenerLibroPorId(libro.getID());

        assertThat(buscado, is(notNullValue()));

    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaBuscarUnLibroPorSuNombre(){

        Libro libro1 = new Libro();
        libro1.setNombre("Scaramanzia");
        repositorioLibro.guardar(libro1);

        Libro libro2 = new Libro();
        libro2.setNombre("Heroes del Whisky");
        repositorioLibro.guardar(libro2);

        List<Libro> buscados = repositorioLibro.obtenerLibroPorNombre(libro2.getNombre());

        assertThat(buscados, hasSize(1));
    }

    @Transactional
    @Rollback
    @Test
    public void queSePuedaBorrarUnLibro(){
        assertTrue(repositorioLibro.borrarLibro(libro.getID()));
    }

}
