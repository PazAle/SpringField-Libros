package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorComentario {

    @Autowired
    private ServicioComentario servicioComentario;
    @Autowired
    private ServicioLibro servicioLibro;

    @RequestMapping(path = "/agregar", method = RequestMethod.POST)
    public ResponseEntity<String> agregarComentario(@RequestParam Long libroId, @RequestParam String texto) {
        Libro libro = servicioLibro.obtenerLibro(libroId);
        if (libro != null) {
            Comentario nuevoComentario = new Comentario();
            nuevoComentario.setLibro(libro);
            nuevoComentario.setTexto(texto);
            boolean seGuardo = servicioComentario.guardarComentario(nuevoComentario);

            if (seGuardo) {
                System.out.println("Se guardó el comentario");
                return new ResponseEntity<>("Comentario agregado exitosamente", HttpStatus.OK);
            } else {
                System.out.println("No se guardó el comentario");
                return new ResponseEntity<>("No se pudo agregar el comentario", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Libro no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    }
