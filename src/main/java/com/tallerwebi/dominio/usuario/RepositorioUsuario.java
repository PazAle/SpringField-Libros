package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.compra.Compra;
import com.tallerwebi.dominio.usuario.Usuario;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    Usuario buscarUsuarioPorId(Long id);

    void generarCompra(Compra compra, Usuario usuario);
}

