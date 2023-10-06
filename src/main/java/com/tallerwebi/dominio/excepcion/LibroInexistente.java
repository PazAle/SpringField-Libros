package com.tallerwebi.dominio.excepcion;

public class LibroInexistente extends Exception {
    public LibroInexistente() {
        super("El libro no existe.");
    }
}
