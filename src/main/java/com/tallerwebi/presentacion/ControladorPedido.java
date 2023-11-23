package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.excepcion.StockInsuficienteException;
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
import org.springframework.web.bind.annotation.PathVariable;
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
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        modelo.put("imagenlogo", imagenLogo);
        if(validarUsuarioLogueado()){
            Pedido pedido = this.obtenerPedido();
            List<Libro> libros =this.servicioPedido.obtenerLibrosDelPedido(pedido);
            modelo.put("libros", libros);
            Double total = this.servicioPedido.calcularTotal(libros);
            modelo.put("precioTotal", total);
        }
        return new ModelAndView("carrito", modelo);
    }
    @RequestMapping(path = "/agregarLibroACarrito/{idLibro}", method = RequestMethod.GET)
    public String  agregarLibroACarrito(@PathVariable Long idLibro) throws StockInsuficienteException {
        Libro libro = this.obtenerLibro(idLibro);
        if(validarUsuarioLogueado()){
            Pedido pedidoActual = this.obtenerPedido();
            this.agregarLibro(libro, pedidoActual);
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/eliminarLibroDeCarrito/{idLibro}", method = RequestMethod.GET)
    public String eliminarLibroDelCarrito(@PathVariable Long idLibro) throws StockInsuficienteException {
        Libro libro = this.obtenerLibro(idLibro);
        Pedido pedidoActual = this.obtenerPedido();
        this.eliminarLibro(libro, pedidoActual);
        return "redirect:/carrito";

    }

    /*@RequestMapping(path = "/generarCompra", method = RequestMethod.POST)
    public void generarCompra*/
    private void eliminarLibro(Libro libro, Pedido pedidoActual) {
        this.servicioPedido.eliminarLibro(libro, pedidoActual);
    }

    private void agregarLibro(Libro libro, Pedido pedidoActual) throws StockInsuficienteException {
        this.servicioPedido.agregarLibro(libro, pedidoActual);

    }

    private Libro obtenerLibro(Long idLibro) {
        return servicioLibro.obtenerLibro(idLibro);
    }

    private Pedido obtenerPedido(){
        Long idUsuario = this.obtenerIdDelUsuario();
        Usuario usuario = servicioLogin.buscarUsuarioPorId(idUsuario);
        return usuario.getPedido();
    }
    public Long obtenerIdDelUsuario(){
        return (Long) request.getSession().getAttribute("IDUSUARIO");
    }
    public Boolean validarUsuarioLogueado(){
        Long idUsuario = this.obtenerIdDelUsuario();
        if(idUsuario != null){
            return true;
        }
        return false;
    }
}
