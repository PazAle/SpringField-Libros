package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.presentacion.DatosFormulario;

public interface ServicioUsuario {

    Usuario obtenerDatosDeUsuario(Long id);

    Boolean actualizarPerfil (Long id, DatosFormulario datos);

    Boolean actualizarContrasenia (Long id, DatosFormulario datos);

    Boolean actualizarEmail (Long id, DatosFormulario datos);

    Boolean eliminarUsuario (Long id);

}
