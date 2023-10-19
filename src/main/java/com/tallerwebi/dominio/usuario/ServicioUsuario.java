package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.pedido.Pedido;

public interface ServicioUsuario {
    Pedido buscarPedidoActivo(Usuario usuario);
}
