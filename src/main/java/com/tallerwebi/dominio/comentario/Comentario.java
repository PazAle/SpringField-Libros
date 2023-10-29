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
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    private Date fecha;


    public Integer getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

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
