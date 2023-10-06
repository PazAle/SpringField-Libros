package com.tallerwebi.dominio.comentario;

import com.tallerwebi.dominio.libro.ServicioLibroImpl;
import com.tallerwebi.presentacion.DatosComentario;
import org.springframework.stereotype.Service;

@Service("servicioComentario")
public class ServicioComentarioImpl implements ServicioComentario {
    @Override
    public Integer guardar(DatosComentario comentario) {
        return null;
    }
}
