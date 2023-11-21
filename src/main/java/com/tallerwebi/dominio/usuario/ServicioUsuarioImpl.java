package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.comentario.ServicioComentarioImpl;
import com.tallerwebi.dominio.libro.RepositorioLibro;
import com.tallerwebi.dominio.pedido.Pedido;
import com.tallerwebi.presentacion.DatosFormulario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }
    @Override
    public Usuario obtenerDatosDeUsuario(Long id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }

    @Override
    public Boolean actualizarPerfil(Long id, DatosFormulario datos) {
        return this.repositorioUsuario.actualizarPerfil(id,datos);
    }

    @Override
    public Boolean actualizarContrasenia(Long id, DatosFormulario datos) {

        String contraseñaActualHasheada = hashPassword(datos.getClaveActual());
        Usuario usuarioActual = this.obtenerDatosDeUsuario(id);
        String contraseñaAlmacenada = usuarioActual.getPassword();

        if(!contraseñaActualHasheada.equals(contraseñaAlmacenada)){
            return false;
        }

        String contraseñaHasheada = hashPassword(datos.getNuevaClave());
        datos.setNuevaClave(contraseñaHasheada);
        String repetirContraseñaHasheada = hashPassword(datos.getConfirmarClave());
        datos.setConfirmarClave(repetirContraseñaHasheada);

        return this.repositorioUsuario.actualizarContrasenia(id,datos);
    }

    @Override
    public Boolean actualizarEmail(Long id, DatosFormulario datos) {
        return this.repositorioUsuario.actualizarEmail(id,datos);
    }

    @Override
    public Boolean eliminarUsuario(Long id) {
        return this.repositorioUsuario.eliminar(id);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
