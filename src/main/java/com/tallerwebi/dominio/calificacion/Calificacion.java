package com.tallerwebi.dominio.calificacion;

import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;
    @Column(name = "valoracion")
    private Integer valoracion;



}
