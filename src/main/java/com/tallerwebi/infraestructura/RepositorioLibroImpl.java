package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioLibro")
public class RepositorioLibroImpl implements RepositorioLibro {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioLibroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Libro> getLibros() {
        return this.sessionFactory.getCurrentSession().createCriteria(Libro.class).list();
        /*List<Libro> libros = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Libro");
        List<Libro> librosObtenidos = query.list();
        return libros;*/

    }

    @Override
    public void guardar(Libro libro) {
        this.sessionFactory.getCurrentSession().save(libro);
    }


    public Libro obtenerLibroPorId(Long id) {
        return (Libro) this.sessionFactory.getCurrentSession().createCriteria(Libro.class)
                .add(Restrictions.eq("ID", id)).uniqueResult();
    }

    @Override
    public List obtenerLibroPorNombre(String nombre) {
        return sessionFactory.getCurrentSession().createCriteria(Libro.class)
                .add(Restrictions.eq("nombre", nombre)).list();
    }

    /*public List<Imagen> obtenerImagenesSecundarias() {
        return this.sessionFactory.getCurrentSession().createCriteria(Imagen.class).list();
    }*/

    @Override
    public boolean borrarLibro(Long id) {
        Libro libroBuscado = obtenerLibroPorId(id);

        final Session session = sessionFactory.getCurrentSession();

        if (libroBuscado != null) {
            // Borrar el libro
            session.delete(libroBuscado);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void modificar(Libro libro) {
        sessionFactory.getCurrentSession().update(libro);
    }

    public List<Libro> obtenerLibrosPorTermino(String termino) {
        return sessionFactory.getCurrentSession().createCriteria(Libro.class)
                .add(Restrictions.ilike("nombre", "%" + termino + "%")).list();
    }
}
