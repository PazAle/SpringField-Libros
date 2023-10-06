package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.categoria.Categoria;

public interface RepositorioCategoria {

    Categoria buscarCategoria(Integer id);

    Categoria buscarCategoriaPorNombre (String nombre);

    void alta (Categoria categoria);
    void modificar (Categoria categoria);
    void borrar (Categoria categoria);

}
