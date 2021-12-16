package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.GasController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.gson.Gson;

/**
 * @author Andrey Kuzmin
 * LogicaNecocioGases
 * 2021-12-14
 */

public class LogicaNegociaGases {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    //contructor
    public LogicaNegociaGases() {
    }

    //get gases interface
    public interface ObtenerGasesCallback {
        void onCompletedObtenerGases(GasController gasController);
        void onFailedObtenerGases(boolean res);
    }

    /**
     * La descripción de obtenerGases. Funcion que obtiene los gases de la bbdd
     *
     * @param access_token String con el token para usarse en las coockies
     *
     *
     */
    public void obtenerGases(String access_token, ObtenerGasesCallback obtenerGasesCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //peticion
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/gases", null , access_token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                Log.d("pepe", "  RECIBIDO -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + cuerpo+"");

                //convertir json a POJO
                Gson gson= new Gson();
                GasController gasController= gson.fromJson(cuerpo, GasController.class);


                //comprobamos si esta registrado en nuestra bbdd o no
                float success= gasController.getSuccess();
                if(success == 1){
                    //le pasamos al callback el valor
                    obtenerGasesCallback.onCompletedObtenerGases(gasController);
                }else if(success == 0){
                    obtenerGasesCallback.onFailedObtenerGases(true);
                }


            }
        });
    }
}
