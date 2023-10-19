package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.dominio.pedido.ServicioPedido;
import com.tallerwebi.dominio.usuario.ServicioUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorPedido {

    private ServicioPedido servicioPedido;
    private ServicioImagen servicioImagen;
    private ServicioLibro servicioLibro;
    private ServicioLogin servicioLogin;
    private HttpServletRequest request;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPedido(ServicioPedido servicioPedido, ServicioImagen servicioImagen, ServicioLibro servicioLibro,
                              ServicioLogin servicioLogin, HttpServletRequest request){
        this.servicioPedido = servicioPedido;
        this.servicioImagen = servicioImagen;
        this.servicioLibro = servicioLibro;
        this.servicioLogin = servicioLogin;
        this.request = request;
    }

    @RequestMapping(path = "/carrito", method = RequestMethod.GET)
    public ModelAndView irAPedido(){
        ModelMap modelo = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        List<Imagen> imagenesMetodosPago = this.servicioImagen.filtrarImagenesMetodosPago(imagenesTotalesObtenidas);
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        Long idUsuario = obtenerIdDelUsuario();
        Usuario usuario = this.servicioLogin.buscarUsuarioPorId(idUsuario);
        if(validarUsuarioLogueado()){
            Pedido pedido = this.servicioUsuario.buscarPedidoActivo(usuario);
            modelo.put("pedido", pedido);
            //modelo.put("imagenlogo", imagenLogo);
            //modelo.put("imgMetodosPago", imagenesMetodosPago);
            //Pedido pedidoActual= this.servicioPedido.obtenerPedidoPorCliente(idCliente);
            //model.put("pedido", pedidoActual);
        }
        return new ModelAndView("carrito", modelo);
    }

    @RequestMapping(path = "/carrito", method = RequestMethod.GET)
    public ModelAndView agregarLibroACarrito(@ModelAttribute("idLibro")Long idLibro){
        ModelMap modelo = new ModelMap();
        Usuario rolUsuario = (Usuario) request.getSession().getAttribute("ROL");

        if(rolUsuario != null){
            Libro libro = this.servicioLibro.obtenerLibro(idLibro);
            Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
            Usuario usuario = servicioLogin.buscarUsuarioPorId(idUsuario);
            Pedido pedidoActual = usuario.pedidoActivo();
            /*if(pedidoActual != null){
                this.servicioPedido.agregarLibro();
            }*/
            this.servicioPedido.agregarLibro(libro, usuario);
        }


        //modelo.put("pedido", libro);
        return new ModelAndView("home");
    }

    public Long obtenerIdDelUsuario(){
        return (Long) request.getSession().getAttribute("idUsuario");
    }
    public Boolean validarUsuarioLogueado(){
        Usuario rolUsuario = (Usuario) request.getSession().getAttribute("ROL");
        if(rolUsuario != null){
            return true;
        }
        return false;
    }
}
