package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

// --------------------------------------------------------------

import com.example.jjpeajar.proyecto_3a_josejulio.src.MedicionC02;
import com.example.jjpeajar.proyecto_3a_josejulio.src.PeticionarioRest;

/**
 * @author Jose Julio Peñaranda
 * 2021-10-14
 */
// --------------------------------------------------------------

public class LogicaFake {
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public LogicaFake(){

    }

    public void publicarMedicion(MedicionC02 medicionCO2){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/medicion/create", medicionCO2.toJSON(), new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
            }
        });
    }
}
