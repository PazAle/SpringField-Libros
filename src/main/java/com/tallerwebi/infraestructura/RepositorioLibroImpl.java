package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.calificacion.Calificacion;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import com.tallerwebi.dominio.usuario.Usuario;

import org.hibernate.Criteria;
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
        //return this.sessionFactory.getCurrentSession().createCriteria(Libro.class).list();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Libro.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Libro> libros = criteria.list();
        return libros;
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


    @Override
    public void calificarLibro(Long idLibro, Long idUsuario, Integer valor) {

        final Session session= this.sessionFactory.getCurrentSession();

        Calificacion calificacion = new Calificacion();

        Usuario usuarioCalifica = new Usuario();
        usuarioCalifica.setId(1L);

        Libro libroACalificar = obtenerLibroPorId(idLibro);

        calificacion.setUsuario(usuarioCalifica);
        calificacion.setLibro(libroACalificar);
        calificacion.setValoracion(valor);

        sessionFactory.getCurrentSession().save(calificacion);

    }

    @Override
    public Calificacion buscarCalificacion(Long idLibro, Long idUsuario) {

        Usuario usuarioCalificador = new Usuario();
        usuarioCalificador.setId(idUsuario);

        Libro libroACalificar = obtenerLibroPorId(idLibro);

        return (Calificacion) this.sessionFactory.getCurrentSession().createCriteria(Calificacion.class)
                .add(Restrictions.eq("usuario", usuarioCalificador))
                .add(Restrictions.eq("libro", libroACalificar)).uniqueResult();
    }

    @Override
    public void actualizarCalificacion(Calificacion calificacion) {
        this.sessionFactory.getCurrentSession().update(calificacion);
    }

    @Override
    public List<Calificacion> obtenerCalificacionesPorLibro(Long libroId) {

        Libro libroObtenido = obtenerLibroPorId(libroId);
        return this.sessionFactory.getCurrentSession().createCriteria(Calificacion.class)
                .add(Restrictions.eq("libro",libroObtenido))
                .list();
    }

}
