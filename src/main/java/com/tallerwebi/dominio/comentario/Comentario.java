package com.tallerwebi.dominio.comentario;

import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.libro.Libro;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String texto;
   // private Usuario usuario;
    //private Libro libro;
    private Date fecha;


    public Integer getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }
    /*
    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    */
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
