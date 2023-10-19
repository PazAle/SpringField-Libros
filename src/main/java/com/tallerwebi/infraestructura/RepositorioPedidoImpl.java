package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.dominio.pedido.Producto;
import com.tallerwebi.dominio.pedido.RepositorioPedido;
import com.tallerwebi.dominio.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void agregarLibro(Libro libro, Usuario usuario) {

        sessionFactory.getCurrentSession().save(new Pedido(new Producto(libro.getNombre(), 1, libro.getPrecio())));
    }

    @Override
    public Pedido obtenerPedidoPorUsuario(Usuario usuario) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Pedido p JOIN Usuario u ON p.id");
        query.setParameter("id", usuario.getId());
        Pedido pedido = (Pedido) query.uniqueResult();
        return pedido;
    }

}
