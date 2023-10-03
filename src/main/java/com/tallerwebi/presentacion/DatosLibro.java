package com.tallerwebi.presentacion;

import java.util.Date;

public class DatosLibro {
    private String nombre;
    private Long idAutor;
    private Double precio;
    private Date fechaLanzamiento;
    private Long idCategoria;
    private Long idEditorial;
    private String idioma;
    private Integer cantidadPaginas;
    private String imagen;
    private String resenia;

    public DatosLibro() {
    }

    public DatosLibro(String nombre, Long idAutor, Double precio, Date fechaLanzamiento, Long idCategoria, Long idEditorial, String idioma, Integer cantidadPaginas, String imagen, String resenia) {
        this.nombre = nombre;
        this.idAutor = idAutor;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
        this.idCategoria = idCategoria;
        this.idEditorial = idEditorial;
        this.idioma = idioma;
        this.cantidadPaginas = cantidadPaginas;
        this.imagen = imagen;
        this.resenia = resenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(Long idEditorial) {
        this.idEditorial = idEditorial;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(Integer cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
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
