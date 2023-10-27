package com.tallerwebi.dominio.comentario;

import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.libro.Libro;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comentario {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String texto;
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   // @JoinColumn(name = "usuario")
   // private Usuario usuario;
    @ManyToOne // Relación ManyToOne con Libro
    @JoinColumn(name = "libro_id")// Nombre de la columna en la tabla de comentarios que hace referencia al libro
    private Libro libro; // Agregar esta propiedad
    private Date fecha;

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Integer getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    /*public Usuario getUsuario() {
        return usuario;
    }*/

    /*public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }*/

    public Date getFecha() {
        return fecha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
