package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario servicioLoginDao;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario servicioLoginDao){
        this.servicioLoginDao = servicioLoginDao;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        String contraseniaaHasheada = hashPassword(password);
        return servicioLoginDao.buscarUsuario(email, contraseniaaHasheada);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = servicioLoginDao.buscar(usuario.getEmail());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        // Hashear la contraseña antes de guardarla
        String contraseñaHasheada = hashPassword(usuario.getPassword());
        usuario.setPassword(contraseñaHasheada);
        String repetirContraseñaHasheada = hashPassword(usuario.getRepetir_password());
        usuario.setRepetir_password(repetirContraseñaHasheada);
        usuario.setRol("usuario");
        servicioLoginDao.guardar(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return servicioLoginDao.buscar(email);
    }

    @Override
    public void actualizarUsuario(Usuario usuario, String nuevaPassword) {
        usuario.setPassword(nuevaPassword);
        usuario.setRepetir_password(nuevaPassword);
        this.servicioLoginDao.modificar(usuario);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());

            // Convertir el hash en representación hexadecimal
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

