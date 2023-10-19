package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        //Map<String, String> response = new HashMap<>();
        // Lógica para agregar un comentario y guardar en la base de datos
        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setLibro(servicioLibro.obtenerLibro(libroId));
        nuevoComentario.setTexto(texto);
        Boolean guardadoComentario = false;
        guardadoComentario = servicioComentario.guardarComentario(nuevoComentario);

        if (guardadoComentario) {
            String message = "Comentario enviado exitosamente";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "No se pudo enviar el comentario";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }



}
