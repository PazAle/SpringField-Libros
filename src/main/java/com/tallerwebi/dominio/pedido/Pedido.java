package com.tallerwebi.dominio.pedido;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pedido_libro",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "libro_id"))
    private List<Libro> libros;
    private Boolean activo;

    public Pedido() {
        //this.libros = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public List<Libro> getLibros() {
        return libros;
    }

    public void setProductos(List<Libro> libros) {
        this.libros = libros;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

