package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.pedido.Pedido;

public class ServicioUsuarioImpl implements ServicioUsuario{
    @Override
    public Pedido buscarPedidoActivo(Usuario usuario) {
        return usuario.pedidoActivo();
    }
}
