package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.RepositorioImagen;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("repositorioImagen")
public class RepositorioImagenImpl implements RepositorioImagen {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioImagenImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Imagen> obtenerImagenesSecundarias() {
        return this.sessionFactory.getCurrentSession().createCriteria(Imagen.class).list();
    }
}
