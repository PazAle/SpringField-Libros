package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.calificacion.Calificacion;
import com.tallerwebi.dominio.comentario.Comentario;
import com.tallerwebi.dominio.comentario.ServicioComentario;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("isUsuarioLogueado")
public class ControladorLibro {

    private ServicioLibro servicioLibro;
    private ServicioImagen servicioImagen;
    private ServicioComentario servicioComentario;
    private HttpServletRequest request;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro, ServicioImagen servicioImagen, ServicioComentario servicioComentario){this.servicioLibro = servicioLibro;
        this.servicioImagen = servicioImagen;
        this.servicioComentario = servicioComentario;
    }

    /*@RequestMapping(path = "/detalle-libro", method = RequestMethod.GET)
    public ModelAndView detalleLibro(@RequestParam("id") Long libroId){
        ModelMap model = new ModelMap();
        Libro libro = servicioLibro.obtenerLibro(libroId);
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        model.put("imagenlogo", imagenLogo);
        model.put("libro", libro);
        model.put("datosLibro", new DatosLibro());
        return new ModelAndView("detalle-libro", model);
    }*/
    @RequestMapping(path = "/detalle-libro", method = RequestMethod.GET)
    public ModelAndView detalleLibro(@RequestParam("id") Long libroId, @RequestParam(name = "page", defaultValue = "1") int page) {
        ModelMap model = new ModelMap();
        Libro libro = servicioLibro.obtenerLibro(libroId);
        List<Comentario> comentarios = servicioComentario.obtenerTodosLosComentarios(libro.getID());

        int pageSize = 5; // Tamaño de página por defecto (5 comentarios por página)
        int totalComentarios = comentarios.size();
        int totalPages = (int) Math.ceil((double) totalComentarios / pageSize); //cuernta del paginado cada 5 coment te cambia de pag

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComentarios);
        List<Comentario> comentariosPaginados = comentarios.subList(startIndex, endIndex);
        Integer calificacioObtenida = servicioLibro.obtenerPromedioCalificacionesPorLibro(libroId);
        //////////////////////////////////////////////

        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);

        model.addAttribute("imagenlogo", imagenLogo);
        model.addAttribute("libro", libro);
        model.addAttribute("comentarios", comentariosPaginados);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("datosLibro", new DatosLibro());
        model.put("calificacion", new Calificacion());
        model.put("calificacionObtenida", calificacioObtenida);

        return new ModelAndView("detalle-libro", model);
    }
    @RequestMapping(path = "/libros", method = RequestMethod.GET)
    public ModelAndView getTodosLosLibros(){
        ModelMap model = new ModelMap();
        List<Libro> libros= this.servicioLibro.getLibros();
        model.put("libros", libros);
        return new ModelAndView("home", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpSession session) {
        ModelMap modelo = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        List<Libro> librosObtenidosParaHome = this.servicioLibro.getLibros();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        List<Imagen> imagenesMetodosPago = this.servicioImagen.filtrarImagenesMetodosPago(imagenesTotalesObtenidas);
        List<Imagen> imagenesCarrusel = this.servicioImagen.filtrarImagenesCarrusel(imagenesTotalesObtenidas);
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        modelo.put("isUsuarioLogueado", isUsuarioLogueado);
        modelo.put("imagenesCarrusel", imagenesCarrusel);
        modelo.put("imagenlogo", imagenLogo);
        modelo.put("imgMetodosPago", imagenesMetodosPago);
        modelo.put("libros", librosObtenidosParaHome);
        modelo.put("datosLibro", new DatosLibro());
        //datosParaHome(modelo);
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(path = "/buscar-libros", method = RequestMethod.POST)
    public ModelAndView buscarLibros(@ModelAttribute("datosLibro") DatosLibro datosLibro) {
        ModelMap model = new ModelMap();
        List <Libro> librosResultado = this.servicioLibro.busqueda(datosLibro.getNombre());
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        model.put("imagenlogo", imagenLogo);

        if (librosResultado.isEmpty()) {
            model.put("error", "No se encontraron libros con ese nombre.");
        } else {
            model.put("librosResultado", librosResultado);
        }

        return new ModelAndView("resultado_busqueda", model);

    }

    @RequestMapping(path = "/calificar-libro", method = RequestMethod.POST)
    public ModelAndView calificarLibro(@RequestParam("id") Long libroId, @RequestParam Integer valoracion){

        ModelMap modelo = new ModelMap();

        Long idLibro = libroId;
        //Usuario usuario = servicioLogin.buscarUsuarioPorId(idUsuario);
       // Long idUsuario = (Long) request.getSession().getAttribute("IDUSUARIO");
        Long idUsuario = 1L;
        Integer valor = valoracion;

        servicioLibro.calificarLibro(idLibro,idUsuario,valor);

        return new ModelAndView("redirect://detalle-libro?id=" + idLibro, modelo);
    }


}
