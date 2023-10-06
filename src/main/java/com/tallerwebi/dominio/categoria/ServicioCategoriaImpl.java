package com.tallerwebi.dominio.categoria;

import com.tallerwebi.dominio.excepcion.CategoriaExistente;
import com.tallerwebi.dominio.excepcion.CategoriaInexistente;
import com.tallerwebi.infraestructura.RepositorioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioCategoria")
public class ServicioCategoriaImpl implements ServicioCategoria {

    private RepositorioCategoria repositorioCategoria;

    @Autowired
    public ServicioCategoriaImpl (RepositorioCategoria repositorioCategoria){
        this.repositorioCategoria = repositorioCategoria;
    }

    @Override
    public Categoria obtenerCategoria(Integer id) {
        return this.repositorioCategoria.buscarCategoria(id);
    }

    @Override
    public void registrarCategoria(Categoria categoria) throws CategoriaExistente {
        Categoria categoriaDuplicada = this.repositorioCategoria.buscarCategoriaPorNombre(categoria.getNombre());
        if(categoriaDuplicada != null){
            throw new CategoriaExistente();
        }
        this.repositorioCategoria.alta(categoria);
    }

    @Override
    public void eliminarCategoria(Categoria categoria) throws CategoriaInexistente {
        Categoria categoriaInexistente = this.repositorioCategoria.buscarCategoria(categoria.getId());
        if(categoriaInexistente == null){
            throw new CategoriaInexistente();
        }
        this.repositorioCategoria.borrar(categoria);
    }

    @Override
    public void modificarInfoCategoria(Categoria categoria) throws CategoriaInexistente {
        Categoria categoriaInexistente = this.repositorioCategoria.buscarCategoria(categoria.getId());
        if(categoriaInexistente == null){
            throw new CategoriaInexistente();
        }
        this.repositorioCategoria.modificar(categoria);
    }
}
