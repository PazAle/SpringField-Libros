package com.tallerwebi.dominio.comentario;

import com.tallerwebi.presentacion.DatosComentario;

import java.util.List;

public interface ServicioComentario {
    Integer guardar(DatosComentario comentario);
    Comentario obtenerComentarioPorId(Integer id);

    List<Comentario> obtenerTodosLosComentarios(Long idLibro);

    void actualizarComentario(Integer id, String textoAActualizar);

    List<Comentario> getAllComentarios();

    Comentario obtenerUltimoComentario();

    Boolean guardarComentario(Comentario nuevoComentario);
}
