package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Town;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.TownController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.gson.Gson;

import java.util.List;

public class LogicaNegocioTowns {

    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public interface ObtenerTownsCallback {
        void onCompletedObtenerTowns(List<Town> towns);
        void onFailedObtenerTowns(boolean res);
    }

    public void obtenerTown(ObtenerTownsCallback obtenerTownsCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/municipios/", null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);

                try {
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
