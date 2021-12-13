package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

/**
 * @author Belén Grande López
 * LogicaNegocioTowns
 * 2021-11-24
 */

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Town;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.TownController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.gson.Gson;

import java.util.List;

public class LogicaNegocioTowns {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    // obtener town interface
    public interface ObtenerTownsCallback {
        void onCompletedObtenerTowns(List<Town> towns);
        void onFailedObtenerTowns(boolean res);
    }

    /**
     * La descripción de obtenerTown. Funcion que obtiene los municipios de la bbdd.
     *
     * @param obtenerTownsCallback Objeto ObtenerTownsCallback para poder devolver el cuerpo.
     *
     */
    public void obtenerTown(ObtenerTownsCallback obtenerTownsCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //peticion
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/municipios/", null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);

                try {
                    //convertir json a POJO
                    Gson gson= new Gson();
                    TownController townControllers= gson.fromJson(cuerpo, TownController.class);

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + townControllers.getSuccess()+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    int success= townControllers.getSuccess();
                    if(success == 1){
                        obtenerTownsCallback.onCompletedObtenerTowns(townControllers.getTowns());
                        obtenerTownsCallback.onFailedObtenerTowns(true);
                    }else {
                        obtenerTownsCallback.onFailedObtenerTowns(false);
                    }
                }catch (Exception e){
                    Log.d("Error", "Error");
                }


            }
        });
    }
}
