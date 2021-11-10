package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

// --------------------------------------------------------------

import com.example.jjpeajar.proyecto_3a_josejulio.src.MedicionC02;
import com.example.jjpeajar.proyecto_3a_josejulio.src.PeticionarioRest;

/**
 * @author Jose Julio Peñaranda
 * 2021-10-14
 */
// --------------------------------------------------------------

public class LogicaNegocio {
    private String ip = "http://192.168.1.102/";

    LogicaNegocio(){

    }

    public void publicarMecicion(MedicionC02 medicionC02){

        PeticionarioRest peticionarioRest = new PeticionarioRest();


        peticionarioRest.realizarPeticion("POST", ip+"GTI-3A-Backend-JoseJulio/mi-proyecto-laravel/public/api/lecturas",
                medicionC02.toJSON(),
                new PeticionarioRest.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                       // elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }
}
