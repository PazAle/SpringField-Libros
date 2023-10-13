package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorLibro {

    private ServicioLibro servicioLibro;
    private ServicioImagen servicioImagen;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro, ServicioImagen servicioImagen){this.servicioLibro = servicioLibro;
        this.servicioImagen = servicioImagen;}

    @RequestMapping(path = "/detalle-libro", method = RequestMethod.GET)
    public ModelAndView detalleLibro(@RequestParam("id") Long libroId){
        ModelMap model = new ModelMap();
        Libro libro = servicioLibro.obtenerLibro(libroId);
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        model.put("imagenlogo", imagenLogo);
        model.put("libro", libro);
        model.put("datosLibro", new DatosLibro());
        return new ModelAndView("detalle-libro", model);

    }
    /*@RequestMapping(path = "/alta-libro", method = RequestMethod.GET)
    public ModelAndView altaLibro(@ModelAttribute("libro") Libro libro) throws LibroExistente {
        ModelMap model = new ModelMap();
        try{
            servicioLibro.registrarLibro(libro);
        } catch (LibroExistente e){
            model.put("error", "El libro ya existe");
            return new ModelAndView("detalle-libro", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo libro");
            return new ModelAndView("detalle-libro", model);
        }

        return new ModelAndView("redirect:/home");
    }*/
    @RequestMapping(path = "/libros", method = RequestMethod.GET)
    public ModelAndView getTodosLosLibros(){
        ModelMap model = new ModelMap();
        List<Libro> libros= this.servicioLibro.getLibros();
        model.put("libros", libros);
        return new ModelAndView("home", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        ModelMap modelo = new ModelMap();
        List<Libro> librosObtenidosParaHome = this.servicioLibro.getLibros();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        List<Imagen> imagenesMetodosPago = this.servicioImagen.filtrarImagenesMetodosPago(imagenesTotalesObtenidas);
        List<Imagen> imagenesCarrusel = this.servicioImagen.filtrarImagenesCarrusel(imagenesTotalesObtenidas);
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
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
        List <Libro> librosResultado = this.servicioLibro.obtenerLibroPorNombre(datosLibro.getNombre());
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

}
