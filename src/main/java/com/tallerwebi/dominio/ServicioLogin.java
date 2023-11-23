package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.usuario.Usuario;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    Usuario buscarUsuarioPorId(Long id);

    Usuario buscarUsuarioPorEmail(String email);

    void actualizarUsuario(Usuario usuario, String nuevaPassword);

}
