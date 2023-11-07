package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;
    private ServicioImagen servicioImagen;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin, ServicioImagen servicioImagen){
        this.servicioLogin = servicioLogin;
        this.servicioImagen = servicioImagen;
    }

    @RequestMapping(value = "/login")
    public ModelAndView irALogin() {
        ModelMap modelo = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        modelo.put("datosLogin", new DatosLogin());
        modelo.put("imagenlogo", imagenLogo);
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("isUsuarioLogueado", true);
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
            request.getSession().setAttribute("isUsuarioLogueado", false);
            model.put("imagenlogo", imagenLogo);
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);

        // Realiza la validación de contraseña y repetir contraseña
        if (!usuario.getPassword().equals(usuario.getRepetir_password())) {
            model.put("error", "Las contraseñas no coinciden");
            model.put("imagenlogo", imagenLogo);
            return new ModelAndView("nuevo-usuario", model);
        }

        try{
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            model.put("imagenlogo", imagenLogo);
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            model.put("imagenlogo", imagenLogo);
            return new ModelAndView("nuevo-usuario", model);
        }
        model.put("imagenlogo", imagenLogo);
        model.put("datosLogin", new DatosLogin());
        model.put("exito", "Usuario creado con éxito.");
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        model.put("usuario", new Usuario());
        model.put("imagenlogo", imagenLogo);
        return new ModelAndView("nuevo-usuario", model);
    }
/*Dos metodos que hacen lo mismo. Uno en Libro y otro en Login. El de libro crea el modelo para la busqueda*/
//    @RequestMapping(path = "/home", method = RequestMethod.GET)
//    public ModelAndView irAHome() {
//        return new ModelAndView("home");
//    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/restablecer-contraseña", method = RequestMethod.GET)
    public ModelAndView restablecerContraseña() {
        ModelMap model = new ModelMap();
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        model.put("imagenlogo", imagenLogo);
        model.put("datosRestablecer", new DatosRestablecer());
        return new ModelAndView("restablecer-contraseña", model);
    }


    @RequestMapping(path = "/cambiar-contraseña", method = RequestMethod.POST)
    public ModelAndView cambiarContraseña(@ModelAttribute("datosRestablecer") DatosRestablecer datosRestablecer, HttpServletRequest request){
        ModelMap model = new ModelMap();
        String emailUsuario = datosRestablecer.getEmail();
        Usuario usuarioEncontrado = this.servicioLogin.buscarUsuarioPorEmail(emailUsuario);
        List<Imagen> imagenesTotalesObtenidas = this.servicioImagen.getImagenesSecundarias();
        Imagen imagenLogo = this.servicioImagen.ObtenerImagenLogo(imagenesTotalesObtenidas);
        model.put("imagenlogo", imagenLogo);

        if(usuarioEncontrado != null){
            if(!datosRestablecer.getNuevapassword().equals(datosRestablecer.getRepetirNuevaPassword())){
                model.put("error", "Las contraseñas no coinciden");
                return new ModelAndView("restablecer-contraseña", model);
            }else{;
                this.servicioLogin.actualizarUsuario(usuarioEncontrado, datosRestablecer.getNuevapassword());
                model.put("exito", "Contraseña cambiada con éxito.");
                model.put("datosLogin", new DatosLogin());
                return new ModelAndView("login", model);
            }

        }else{
            model.put("error", "El usuario no existe");
            return new ModelAndView("restablecer-contraseña", model);
        }
    }

    @RequestMapping(path = "/cerrar-sesion", method = RequestMethod.GET)
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Invalida la sesión, lo que equivale a cerrar sesión
        return "redirect:/home"; // Redirige al inicio o a donde desees después de cerrar sesión
    }

}


