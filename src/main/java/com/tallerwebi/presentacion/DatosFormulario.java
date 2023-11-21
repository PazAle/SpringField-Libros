package com.tallerwebi.presentacion;

public class DatosFormulario {
    private String nombre;
    private String apellido;

    private Long idUsuario;
    private String email;
    private String claveActual;
    private String nuevaClave;
    private String confirmarClave;
    private String nuevoEmail;

    public DatosFormulario() {
    }

    public DatosFormulario(String nombre, String apellido, String email, String claveActual,
                           String nuevaClave, String confirmarClave, String nuevoEmail, Long idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.claveActual = claveActual;
        this.nuevaClave = nuevaClave;
        this.confirmarClave = confirmarClave;
        this.nuevoEmail = nuevoEmail;
        this.idUsuario = idUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public String getNuevoEmail() {
        return nuevoEmail;
    }

    public void setNuevoEmail(String nuevoEmail) {
        this.nuevoEmail = nuevoEmail;
    }
}

