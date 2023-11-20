package com.tallerwebi.dominio.mercadoPago;

import com.tallerwebi.dominio.pedido.Pedido;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ServicioMercadoPago {
    String pagarPedido(Double precioTotal) throws IOException;
}
