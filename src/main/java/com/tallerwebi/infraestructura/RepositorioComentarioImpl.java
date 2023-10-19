package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.categoria.Categoria;
import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.RepositorioComentario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioComentario")
public class RepositorioComentarioImpl implements RepositorioComentario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioComentarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*@Override
    public void guardar(Comentario comentario) {
        this.sessionFactory.getCurrentSession().save(comentario);
    }*/
    /*@Override
    public Boolean guardar(Comentario comentario) {
        // Abre una sesión de Hibernate para interactuar con la base de datos
        try (Session session = sessionFactory.openSession()) {
            // Inicia una transacción
            Transaction tx = session.beginTransaction();

            // Guarda el comentario en la base de datos
            session.save(comentario);

            // Confirma la transacción (guarda los cambios)
            tx.commit();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }*/
    @Override
    public Boolean guardar(Comentario comentario) {
        try {
            this.sessionFactory.getCurrentSession().save(comentario);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public List<Comentario> getAllComentariosPorLibro(Long idLibro) {
        return this.sessionFactory.getCurrentSession().createCriteria(Comentario.class).
                add(Restrictions.eq("id", idLibro)).list();
    }

    @Override
    public Comentario obtenerComentarioPorId(Integer id) {
        return (Comentario) this.sessionFactory.getCurrentSession().createCriteria(Comentario.class).
                add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void update(Integer id, String textoAActualizar) {
        Comentario comentarioObtenido = (Comentario) this.sessionFactory.getCurrentSession().createCriteria(Comentario.class).
                add(Restrictions.eq("id", id)).uniqueResult();
        if (comentarioObtenido != null) {
            comentarioObtenido.setTexto(textoAActualizar);
            this.sessionFactory.getCurrentSession().update(comentarioObtenido);
        }
    }

    @Override
    public Boolean borrar(Comentario comentario) {
    Comentario comentarioExistente = this.sessionFactory.getCurrentSession().get(Comentario.class, comentario.getId());

        if(comentarioExistente != null){
            this.sessionFactory.getCurrentSession().delete(comentarioExistente);
            return true;
        }
        return false;
    }

    @Override
    public List<Comentario> getAllComentarios() {
        return this.sessionFactory.getCurrentSession().createCriteria(Comentario.class).list();
    }

    @Override
    public Comentario obtenerUltimoComentario() {
        return (Comentario) this.sessionFactory.getCurrentSession().createCriteria(Comentario.class).addOrder(Order.desc("id")).
                setMaxResults(1).uniqueResult();
    }

}
