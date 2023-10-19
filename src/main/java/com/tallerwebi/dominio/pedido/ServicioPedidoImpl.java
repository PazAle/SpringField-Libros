package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public void agregarLibro(Libro libro, Usuario usuario) {
        if(libro.getStock()>0){
            this.repositorioPedido.agregarLibro(libro, usuario);
        }
    }
}
