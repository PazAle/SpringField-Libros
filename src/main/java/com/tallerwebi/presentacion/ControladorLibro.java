package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class ControladorLibro {

    private ServicioLibro servicioLibro;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro){
        this.servicioLibro = servicioLibro;
    }

    @RequestMapping(path = "/detalle-libro", method = RequestMethod.GET)
    public ModelAndView detalleLibro(){
        ModelMap model = new ModelMap();
        model.put("libro", new Libro());
        model.put("datosLibro", new DatosLibro());
        return new ModelAndView("detalle-libro", model);

    }
    @RequestMapping(path = "/alta-libro", method = RequestMethod.GET)
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
    }
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
        modelo.put("libros", librosObtenidosParaHome);
        modelo.put("datosLibro", new DatosLibro());
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(path = "/buscar-libros", method = RequestMethod.POST)
    public ModelAndView buscarLibros(@ModelAttribute("datosLibro") DatosLibro datosLibro) {
        ModelMap model = new ModelMap();

        List <Libro> librosResultado = this.servicioLibro.obtenerLibroPorNombre(datosLibro.getNombre());

        if (librosResultado.isEmpty()) {
            model.put("error", "No se encontraron libros con ese nombre.");
        } else {
            model.put("librosResultado", librosResultado);
        }

        return new ModelAndView("resultado_busqueda", model);

    }

}
