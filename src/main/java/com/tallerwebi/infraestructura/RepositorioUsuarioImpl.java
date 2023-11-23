package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.compra.Compra;
import com.tallerwebi.dominio.usuario.RepositorioUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import org.hibernate.Hibernate;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.RepositorioUsuario;
import com.tallerwebi.presentacion.DatosFormulario;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        String hql = "FROM Usuario u WHERE u.email = :email";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);
        return (Usuario) query.uniqueResult();
    }
    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Usuario u WHERE u.id = :id AND activo = true");
        query.setParameter("id", id);
        Usuario usuario = (Usuario) query.uniqueResult();
        return usuario;
    }

    @Override

    public void generarCompra(Compra compra, Usuario usuario) {
        //Hibernate.initialize(usuario.getCompras());
        usuario.getCompras().add(compra);
        this.sessionFactory.getCurrentSession().update(usuario);

    public Boolean actualizarPerfil(Long id, DatosFormulario datos) {
        Usuario usuarioObtenido = (Usuario) this.sessionFactory.getCurrentSession()
                .createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        if (usuarioObtenido != null) {
            usuarioObtenido.setNombre(datos.getNombre());
            usuarioObtenido.setApellido(datos.getApellido());

            this.sessionFactory.getCurrentSession().update(usuarioObtenido);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Boolean actualizarContrasenia(Long id, DatosFormulario datos) {
        Usuario usuarioObtenido = (Usuario) this.sessionFactory.getCurrentSession()
                .createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        if (usuarioObtenido != null) {
            usuarioObtenido.setPassword(datos.getNuevaClave());
            usuarioObtenido.setRepetir_password(datos.getConfirmarClave());
            this.sessionFactory.getCurrentSession().update(usuarioObtenido);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Boolean actualizarEmail(Long id, DatosFormulario datos) {
        Usuario usuarioObtenido = (Usuario) this.sessionFactory.getCurrentSession()
                .createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        if (usuarioObtenido != null) {
            usuarioObtenido.setEmail(datos.getNuevoEmail());
            this.sessionFactory.getCurrentSession().update(usuarioObtenido);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Boolean eliminar(Long id) {
        Usuario usuarioObtenido = (Usuario) this.sessionFactory.getCurrentSession()
                .createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        final Session session = sessionFactory.getCurrentSession();

        if (usuarioObtenido != null) {
            session.delete(usuarioObtenido);
            return true;
        }else{
            return false;
        }

    }

}
