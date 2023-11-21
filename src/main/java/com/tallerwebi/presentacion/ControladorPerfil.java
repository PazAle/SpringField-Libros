package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.usuario.ServicioUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes("isUsuarioLogueado")
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;
    private ServicioImagen servicioImagen;

    public ControladorPerfil(ServicioUsuario servicioUsuario, ServicioImagen servicioImagen){
        this.servicioUsuario = servicioUsuario;
        this.servicioImagen = servicioImagen;
    }

    @RequestMapping(value = "/perfil")
    public ModelAndView irAPerfil(HttpSession session) {
        ModelMap modelo = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        String nombreUsuario = (String) session.getAttribute("NOMBRE");
        String apellidoUsuario = (String) session.getAttribute("APELLIDO");
        Long idUsuario = (Long) session.getAttribute("IDUSUARIO");
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        modelo.put("isUsuarioLogueado", isUsuarioLogueado);
        modelo.put("imagenlogo", imagenLogo);
        modelo.put("nombreUsuario", nombreUsuario);
        modelo.put("apellidoUsuario", apellidoUsuario);
        modelo.put("idUsuario", idUsuario);
        modelo.put("datosLibro", new DatosLibro());
        return new ModelAndView("perfil",modelo);
    }

    @RequestMapping(path = "/obtenerDatos", method = RequestMethod.POST)
    public ResponseEntity<Usuario> obtenerDatosParaAjuste(@RequestParam("idUsuario") Long idUsuario) {
        Usuario usuario = this.servicioUsuario.obtenerDatosDeUsuario(idUsuario);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/cambioClave", method = RequestMethod.POST)
    public ResponseEntity<Usuario> ajustarContrasenia(@RequestParam("idUsuario") Long idUsuario) {
        Usuario usuario = this.servicioUsuario.obtenerDatosDeUsuario(idUsuario);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/cambiarEmail", method = RequestMethod.POST)
    public ResponseEntity<Usuario> ajustarEmail(@RequestParam("idUsuario") Long idUsuario) {
        Usuario usuario = this.servicioUsuario.obtenerDatosDeUsuario(idUsuario);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/actualizarPerfil", method = RequestMethod.POST)
    public ResponseEntity<Boolean> actualizarPerfil(@RequestBody DatosFormulario datos) {
        Long idUsuario = datos.getIdUsuario();
        Boolean resultado = this.servicioUsuario.actualizarPerfil(idUsuario,datos);
        if (resultado) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(path = "/cambiarClave", method = RequestMethod.POST)
    public ResponseEntity<Boolean> cambiarClave(@RequestBody DatosFormulario datos) {
        Long idUsuario = datos.getIdUsuario();
        Boolean resultado = this.servicioUsuario.actualizarContrasenia(idUsuario,datos);
        if (resultado!=null) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(path = "/actualizarEmail", method = RequestMethod.POST)
    public ResponseEntity<Boolean> cambiarEmail(@RequestBody DatosFormulario datos) {
        Long idUsuario = datos.getIdUsuario();
        Boolean resultado = this.servicioUsuario.actualizarEmail(idUsuario,datos);
        if (resultado) {
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/eliminarUsuario", method = RequestMethod.POST)
    public ResponseEntity<String> eliminarUsuario(@RequestParam("idUsuario") Long idUsuario,HttpSession session) {
        Boolean resultado = this.servicioUsuario.eliminarUsuario(idUsuario);
        if (resultado) {
            session.invalidate();
            return new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
