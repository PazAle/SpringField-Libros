package com.tallerwebi.dominio.libro;

import com.tallerwebi.dominio.autor.Autor;
import com.tallerwebi.dominio.categoria.Categoria;
import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.editorial.Editorial;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Date;
@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String nombre;
    //private Autor autor;
    private Double precio;
    private Date fechaLanzamiento;
    //private Categoria categoria;
    //private Editorial editorial;
    private String idioma;
    private Integer cantidadPaginas;
    private String imagen;
    private String resenia;

    //private List<Comentario> comentarios;

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    /*
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    */

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getCantidadPaginas() {
        return this.cantidadPaginas;
    }

    public void setCantidadPaginas(Integer paginas) {
        this.cantidadPaginas = paginas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getResenia() {
        return resenia;
    }

    public void setResenia(String resenia) {
        this.resenia = resenia;
    }
}
