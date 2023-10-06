package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.categoria.Categoria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioCategoria")
public class RepositorioCategoriaImpl implements RepositorioCategoria {

    private SessionFactory sessionFactory;


    @Autowired
    public RepositorioCategoriaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Categoria buscarCategoria(Integer id) {
        return (Categoria) sessionFactory.getCurrentSession().createCriteria(Categoria.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Categoria buscarCategoriaPorNombre(String nombre) {
        return (Categoria) sessionFactory.getCurrentSession().createCriteria(Categoria.class)
                .add(Restrictions.eq("nombre", nombre)).uniqueResult();
    }

    @Override
    public void alta(Categoria categoria) {
        sessionFactory.getCurrentSession().save(categoria);
    }

    @Override
    public void modificar(Categoria categoria) {
        sessionFactory.getCurrentSession().update(categoria);
    }

    @Override
    public void borrar(Categoria categoria) {
        sessionFactory.getCurrentSession().delete(categoria);
    }
}
