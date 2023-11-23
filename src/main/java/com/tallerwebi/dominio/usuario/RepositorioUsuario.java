package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.compra.Compra;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.presentacion.DatosFormulario;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    Usuario buscarUsuarioPorId(Long id);


    void generarCompra(Compra compra, Usuario usuario);

    Boolean actualizarPerfil(Long id, DatosFormulario datos);

    Boolean actualizarContrasenia(Long id, DatosFormulario datos);

    Boolean actualizarEmail(Long id, DatosFormulario datos);

    Boolean eliminar(Long id);

}

