package com.example.jjpeajar.proyecto_3a_josejulio;

// --------------------------------------------------------------
/**
 * @author Jose Julio Peñaranda
 * 2021-10-14
 */
// --------------------------------------------------------------

public class LogicaFake {
    private String ip = "http://10.236.14.213/";

    LogicaFake(){

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
