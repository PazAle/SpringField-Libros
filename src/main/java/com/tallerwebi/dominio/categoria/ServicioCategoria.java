package com.tallerwebi.dominio.categoria;

import com.tallerwebi.dominio.excepcion.CategoriaExistente;
import com.tallerwebi.dominio.excepcion.CategoriaInexistente;

public interface ServicioCategoria {
    Categoria obtenerCategoria(Integer id);
    void registrarCategoria(Categoria categoria) throws CategoriaExistente;
    void eliminarCategoria (Categoria categoria) throws CategoriaInexistente;
    void modificarInfoCategoria(Categoria categoria) throws CategoriaInexistente;

}
