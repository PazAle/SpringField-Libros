package com.tallerwebi.presentacion;

public class DatosRestablecer {

    private String email;
    private String Nuevapassword;
    private String repetirNuevaPassword;

    public DatosRestablecer(String email, String nuevapassword, String repetirNuevaPassword) {
        this.email = email;
        Nuevapassword = nuevapassword;
        this.repetirNuevaPassword = repetirNuevaPassword;
    }

    public DatosRestablecer() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNuevapassword() {
        return Nuevapassword;
    }

    public void setNuevapassword(String nuevapassword) {
        Nuevapassword = nuevapassword;
    }

    public String getRepetirNuevaPassword() {
        return repetirNuevaPassword;
    }

    public void setRepetirNuevaPassword(String repetirNuevaPassword) {
        this.repetirNuevaPassword = repetirNuevaPassword;
    }
}
