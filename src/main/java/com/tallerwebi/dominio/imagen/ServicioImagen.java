package com.tallerwebi.dominio.imagen;

import java.util.List;

public interface ServicioImagen {
    List<Imagen> getImagenesSecundarias();
    List<Imagen> filtrarImagenesMetodosPago(List<Imagen> imagenesTotalesObtenidas);
    Imagen ObtenerImagenLogo(List<Imagen> imagenesTotalesObtenidas);
    List<Imagen> filtrarImagenesCarrusel(List<Imagen> imagenesTotalesObtenidas);

}
