package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.dominio.pedido.RepositorioPedido;
import com.tallerwebi.dominio.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Pedido obtenerPedidoPorUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void guardarPedido(Pedido pedidoActual) {
        this.sessionFactory.getCurrentSession().update(pedidoActual);
    }

    @Override
    public void agregarLibro(Libro libro, Pedido pedido) {
        pedido.getLibros().add(libro);
        this.guardarPedido(pedido);
    }

    @Override
    public void actualizarLibro(Libro libro, Pedido pedido) {
        pedido.getLibros().remove(libro.getID());
        pedido.getLibros().add(libro);
        this.guardarPedido(pedido);
    }

    @Override
    public List<Libro> obtenerLibrosDelPedido(Pedido pedido) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT p.libros FROM Pedido p WHERE p.id = :pedidoId");
        query.setParameter("pedidoId", pedido.getId());
        List<Libro> libros = (List<Libro>) query.list();
        return libros;    }

}
