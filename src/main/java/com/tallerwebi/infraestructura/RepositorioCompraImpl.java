package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.compra.Compra;
import com.tallerwebi.dominio.compra.RepositorioCompra;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.dominio.usuario.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepositorioCompra")
public class RepositorioCompraImpl implements RepositorioCompra {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Compra generarCompra(Pedido pedido, Usuario usuario) {
        Compra compra = new Compra();
        List<Libro> libros = pedido.getLibros();
        compra.setLibrosComprados(libros);
        Hibernate.initialize(compra.getUsuario());
        compra.setUsuario(usuario);
        this.guardarCompra(compra);
        return compra;
    }

    @Override
    public List<Libro> obtenerLibrosCompradosPorUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT DISTINCT l FROM Libro l " +
                "JOIN l.compras c " +
                "JOIN c.usuario u " +
                "WHERE u.id = :usuarioId");
        query.setParameter("usuarioId", usuario.getId());
        List<Libro> libros = (List<Libro>) query.list();
        return libros;
    }

    private void guardarCompra(Compra compra) {
        this.sessionFactory.getCurrentSession().save(compra);
    }
}
