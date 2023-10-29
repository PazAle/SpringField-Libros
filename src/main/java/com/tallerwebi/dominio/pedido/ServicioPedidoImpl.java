package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.excepcion.StockInsuficienteException;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPedido")
@Transactional
public class ServicioPedidoImpl implements ServicioPedido{

    private RepositorioPedido repositorioPedido;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido repositorioPedido){
        this.repositorioPedido = repositorioPedido;
    }
    @Override
    public Pedido obtenerPedidoPorUsuario(Usuario usuario) {
        return this.repositorioPedido.obtenerPedidoPorUsuario(usuario);
    }

    @Override
    public void agregarLibro(Libro libro, Pedido pedido) throws StockInsuficienteException {
        Boolean agregado = this.yaEstaEnElPedido(pedido, libro);
        Integer stock = libro.getStock();
        Integer cantidad= libro.getCantidad();
        if (stock > 0) {
            if (agregado) {
                cantidad++;
                libro.setCantidad(cantidad);
                this.repositorioPedido.actualizarLibro(libro, pedido);
            } else {
                this.repositorioPedido.agregarLibro(libro, pedido);
                cantidad = 1;
                libro.setCantidad(cantidad);

            }
        } else {
            throw new StockInsuficienteException("Libro sin stock");
        }
    }

    private Boolean yaEstaEnElPedido(Pedido pedido, Libro libro) {
        List<Libro> libros = pedido.getLibros();
        Boolean agregado = false;
        for(Libro actual: libros){
            if(actual.getID().equals(libro.getID())){
                agregado = true;
            }
        }
        return agregado;
    }

    @Override
    public void guardarPedido(Pedido pedidoActual) {
        this.repositorioPedido.guardarPedido(pedidoActual);
    }

    @Override
    public List<Libro> obtenerLibrosDelPedido(Pedido pedido) {
        return repositorioPedido.obtenerLibrosDelPedido(pedido);
    }
}
