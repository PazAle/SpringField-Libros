package com.tallerwebi.dominio.excepcion;

public class CategoriaExistente extends Exception {
    public CategoriaExistente() {
        super("La categoría ya existe.");
    }
}
