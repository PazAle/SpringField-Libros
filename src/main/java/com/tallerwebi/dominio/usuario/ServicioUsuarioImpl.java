package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.comentario.ServicioComentarioImpl;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import com.tallerwebi.dominio.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }
    @Override
    public Usuario obtenerDatosDeUsuario(Long id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }
}
