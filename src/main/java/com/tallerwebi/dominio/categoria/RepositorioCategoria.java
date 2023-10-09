package com.tallerwebi.dominio.categoria;

import com.tallerwebi.dominio.categoria.Categoria;

public interface RepositorioCategoria {

    Categoria buscarCategoria(Integer id);

    Categoria buscarCategoriaPorNombre (String nombre);

    void alta (Categoria categoria);
    void modificar (Categoria categoria);
    void borrar (Categoria categoria);

}
