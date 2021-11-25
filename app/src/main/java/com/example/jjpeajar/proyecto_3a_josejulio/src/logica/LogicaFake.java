package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

// --------------------------------------------------------------

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.MedicionC02;

/**
 * @author Jose Julio Peñaranda
 * 2021-10-14
 */
// --------------------------------------------------------------

public class LogicaFake {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public LogicaFake(){

    }

    /**
     * La descripción de publicarMedicion. Funcion que publica las mediciones de CO2 en la bbdd.
     *
     * @param medicionCO2 Objeto MedicionC02 de la medicion a publicar.
     *
     */
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
