package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.libro.Libro;
import com.tallerwebi.dominio.libro.ServicioLibro;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ControladorLibro {

    private ServicioLibro servicioLibro;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro){
        this.servicioLibro = servicioLibro;
    }

    @RequestMapping(path = "/nuevo-libro", method = RequestMethod.GET)
    public ModelAndView nuevoLibro(){
        ModelMap model = new ModelMap();
        model.put("libro", new Libro());
        return new ModelAndView("nuevo-libro", model);

    }
    @RequestMapping(path = "/alta-libro", method = RequestMethod.GET)
    public ModelAndView altaLibro(@ModelAttribute("libro") Libro libro) throws LibroExistente {
        ModelMap model = new ModelMap();
        try{
            servicioLibro.registrarLibro(libro);
        } catch (LibroExistente e){
            model.put("error", "El libro ya existe");
            return new ModelAndView("nuevo-libro", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo libro");
            return new ModelAndView("nuevo-libro", model);
        }

        return new ModelAndView("redirect:/home");
    }
    @RequestMapping(path = "/libros", method = RequestMethod.GET)
    public ModelAndView getTodosLosLibros(){
        ModelMap model = new ModelMap();
        Set<Libro> libros= this.servicioLibro.getLibros();
        model.put("libros", libros);
        return new ModelAndView("home", model);
    }

}
