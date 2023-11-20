package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.mercadoPago.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorPago {
    private ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorPago(ServicioMercadoPago servicioMercadoPago){
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping(path = "/preferencia", method = RequestMethod.POST)
    public ResponseEntity<String> generarPreferencia(@RequestParam("precioTotal") Double precioTotal) {
        System.out.println("aca");
        try {
            String  idPreferencia =  this.servicioMercadoPago.pagarPedido(precioTotal);
            return new ResponseEntity<>(idPreferencia, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
