package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.compra.Compra;
import com.tallerwebi.dominio.pedido.Pedido;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String repetir_password;
    private String rol;
    private Boolean activo = false;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido")
    private Pedido pedido;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Compra> compras;

    public Usuario(){
        this.pedido = new Pedido();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRepetir_password() {
        return repetir_password;
    }

    public void setRepetir_password(String repetir_password) {
        this.repetir_password = repetir_password;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', email='" + email + "', compras=" + compras + "}";
    }
}
