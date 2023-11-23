package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.compra.ServicioCompra;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorCompra {
    private ServicioCompra servicioCompra;
    private HttpServletRequest request;
    private ServicioImagen servicioImagen;
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorCompra(ServicioCompra servicioCompra, HttpServletRequest request, ServicioImagen servicioImagen,
                             ServicioLogin servicioLogin){
        this.servicioCompra = servicioCompra;
        this.request = request;
        this.servicioImagen = servicioImagen;
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping(path = "/mis-libros", method = RequestMethod.GET)
    public ModelAndView irAMisLibros(){
        ModelMap modelo = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        modelo.put("imagenlogo", imagenLogo);
        Usuario usuario = this.obtenerUsuario();
        List<Libro> libros =this.servicioCompra.obtenerLibrosCompradosPorUsuario(usuario);
        modelo.put("libros", libros);

        return new ModelAndView("mis-libros", modelo);
    }

    private Usuario obtenerUsuario() {
        Long idUsuario = (Long) request.getSession().getAttribute("IDUSUARIO");
        Usuario usuario = this.servicioLogin.buscarUsuarioPorId(idUsuario);
        return usuario;
    }
}
