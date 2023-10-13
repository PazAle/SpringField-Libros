package com.tallerwebi.dominio.imagen;

import com.tallerwebi.dominio.libro.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioImagen")
@Transactional
public class ServicioImagenImpl implements ServicioImagen{
    private RepositorioImagen repositorioImagen;

    @Autowired
    public ServicioImagenImpl(RepositorioImagen repositorioImagen){
        this.repositorioImagen = repositorioImagen;
    }

    @Override
    public List<Imagen> getImagenesSecundarias() {
        return repositorioImagen.obtenerImagenesSecundarias();
    }

    @Override
    public List<Imagen> filtrarImagenesMetodosPago(List<Imagen> imagenesTotalesObtenidas) {
        List<Imagen> imagenesFiltradas = new ArrayList<>();

        for (Imagen imagen: imagenesTotalesObtenidas){
            if(imagen.getNombreImagen().equals("Visa") || imagen.getNombreImagen().equals("Mercadopago") ||
                    imagen.getNombreImagen().equals("AmericanExpress") || imagen.getNombreImagen().equals("Mastercard")){
                imagenesFiltradas.add(imagen);
            }
        }
        return imagenesFiltradas;
    }

    @Override
    public Imagen ObtenerImagenLogo(List<Imagen> imagenesTotalesObtenidas) {
        Imagen imagenEncontrada = null;

        for (Imagen imagen: imagenesTotalesObtenidas){
            if (imagen.getId().equals(8)){
                imagenEncontrada = imagen;
            }
        }
        return imagenEncontrada;
    }

    @Override
    public List<Imagen> filtrarImagenesCarrusel(List<Imagen> imagenesTotalesObtenidas) {
        List<Imagen> imagenesFiltradas = new ArrayList<>();

        for(Imagen imagen: imagenesTotalesObtenidas){
            if(imagen.getNombreImagen().equals("carrusel1") ||
                    imagen.getNombreImagen().equals("carrusel2") ||
                    imagen.getNombreImagen().equals("carrusel3")){
                imagenesFiltradas.add(imagen);
            }
        }
        return imagenesFiltradas;
    }
}
