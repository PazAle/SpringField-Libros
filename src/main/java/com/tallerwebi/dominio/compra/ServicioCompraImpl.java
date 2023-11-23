package com.tallerwebi.dominio.compra;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ServicioCompraImpl implements ServicioCompra{
    private RepositorioCompra repositorioCompra;

    @Autowired
    public ServicioCompraImpl(RepositorioCompra repositorioCompra){
        this.repositorioCompra = repositorioCompra;
    }

    @Override
    public List<Libro> obtenerLibrosCompradosPorUsuario(Usuario usuario) {
        return this.repositorioCompra.obtenerLibrosCompradosPorUsuario(usuario);
    }
}
