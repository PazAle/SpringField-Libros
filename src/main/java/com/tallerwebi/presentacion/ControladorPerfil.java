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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;
    private ServicioImagen servicioImagen;

    public ControladorPerfil(ServicioUsuario servicioUsuario, ServicioImagen servicioImagen){
        this.servicioUsuario = servicioUsuario;
        this.servicioImagen = servicioImagen;
    }

    @RequestMapping(value = "/perfil")
    public ModelAndView irAPerfil() {
        ModelMap modelo = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        modelo.put("imagenlogo", imagenLogo);
        modelo.put("datosLibro", new DatosLibro());
        return new ModelAndView("perfil",modelo);
    }

    @RequestMapping(path = "/obtenerDatos", method = RequestMethod.POST)
    public ResponseEntity<Usuario> obtenerDatosParaAjuste() {
        Usuario usuario = this.servicioUsuario.obtenerDatosDeUsuario(1L);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
