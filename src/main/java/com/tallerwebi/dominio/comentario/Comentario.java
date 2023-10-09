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
    @JoinColumn(name = "usuario")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario usuario;
    private Date fecha;


    public Integer getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
