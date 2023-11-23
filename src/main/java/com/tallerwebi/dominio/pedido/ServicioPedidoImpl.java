package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.compra.Compra;
import com.tallerwebi.dominio.compra.RepositorioCompra;
import com.tallerwebi.dominio.excepcion.StockInsuficienteException;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import com.tallerwebi.dominio.usuario.RepositorioUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPedido")
@Transactional
public class ServicioPedidoImpl implements ServicioPedido{

    private RepositorioPedido repositorioPedido;
    private RepositorioLibro repositorioLibro;
    private RepositorioCompra repositorioCompra;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido repositorioPedido, RepositorioLibro repositorioLibro,
                              RepositorioCompra repositorioCompra, RepositorioUsuario repositorioUsuario){
        this.repositorioPedido = repositorioPedido;
        this.repositorioLibro = repositorioLibro;
        this.repositorioCompra = repositorioCompra;
        this.repositorioUsuario = repositorioUsuario;
    }
    @Override
    public Pedido obtenerPedidoPorUsuario(Usuario usuario) {
        return this.repositorioPedido.obtenerPedidoPorUsuario(usuario);
    }

    @Override
    public void agregarLibro(Libro libro, Pedido pedido) throws StockInsuficienteException {
        Integer stock = libro.getStock();
        Boolean agregado = this.yaEstaEnElPedido(pedido, libro);
        Integer cantidad= libro.getCantidad();
        if (stock > 0) {
            if (agregado) {
                cantidad = cantidad + 1;
                libro.setCantidad(libro.getCantidad() + 1);
                this.actualizarLibro(libro);
            } else {
                cantidad = 1;
                libro.setCantidad(cantidad);
                this.actualizarLibro(libro);
                this.repositorioPedido.agregarLibro(libro, pedido);
            }
        } else {
            throw new StockInsuficienteException("Libro sin stock");
        }
    }

    @Override
    public void actualizarLibro(Libro libro) {
        this.repositorioLibro.actualizarLibro(libro);
    }

    @Override
    public Double calcularTotal(List<Libro> libros) {
        Double total = 0.0;
        for(Libro libro : libros){
            total+= Math.round(libro.getPrecio()*libro.getCantidad());
        }
        return total;
    }

    @Override
    public void generarCompra(Pedido pedido, Usuario usuario) {
        Compra compra = this.repositorioCompra.generarCompra(pedido, usuario);
        //this.repositorioUsuario.generarCompra(compra, usuario);
    }

    @Override
    public void vaciarPedido(Pedido pedido) {
        this.repositorioPedido.vaciarPedido(pedido);
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

    @Override
    public void eliminarLibro(Libro libro, Pedido pedidoActual) {
        libro.setCantidad(0);
        this.actualizarLibro(libro);
        this.repositorioPedido.eliminarLibro(libro, pedidoActual);
    }
}
