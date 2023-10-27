package com.tallerwebi.dominio.comentario;

import com.tallerwebi.dominio.libro.ServicioLibroImpl;
import com.tallerwebi.presentacion.DatosComentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioComentario")
@Transactional
public class ServicioComentarioImpl implements ServicioComentario {

    private RepositorioComentario repositorioComentario;
    private Comentario comentario;
    @Autowired
    public ServicioComentarioImpl(RepositorioComentario repositorioComentario) {
        this.repositorioComentario = repositorioComentario;
        this.comentario = new Comentario();
    }

    @Override
    public Integer guardar(DatosComentario comentario) {
        return null;
    }

    @Override
    public Comentario obtenerComentarioPorId(Integer id) {
        return this.repositorioComentario.obtenerComentarioPorId(id);
    }

    @Override
    public List<Comentario> obtenerTodosLosComentarios(Long idLibro) {
        return this.repositorioComentario.getAllComentariosPorLibro(idLibro);
    }

    @Override
    public void actualizarComentario(Integer id, String textoAActualizar) {
        this.repositorioComentario.update(id, textoAActualizar);
    }

    @Override
    public List<Comentario> getAllComentarios() {
        return this.repositorioComentario.getAllComentarios();
    }

    @Override
    public Comentario obtenerUltimoComentario() {
        return this.repositorioComentario.obtenerUltimoComentario();
    }

        @Override
        public Boolean guardarComentario(Comentario nuevoComentario) {
            Boolean seGuardo = false;
            if (nuevoComentario != null && nuevoComentario.getTexto() != null && !nuevoComentario.getTexto().isEmpty()) {
               return seGuardo = this.repositorioComentario.guardar(nuevoComentario);

            }
            return seGuardo;
        }
}
