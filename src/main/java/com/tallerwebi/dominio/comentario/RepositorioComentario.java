package com.tallerwebi.dominio.comentario;

import java.util.List;

public interface RepositorioComentario {

    Boolean guardar(Comentario comentario);

    List<Comentario> getAllComentariosPorLibro(Long idLibro);

    Comentario obtenerComentarioPorId(Integer id);

    void update(Integer id, String textoAActualizar);

    Boolean borrar (Comentario comentario);

    List<Comentario> getAllComentarios();

    Comentario obtenerUltimoComentario();
}
