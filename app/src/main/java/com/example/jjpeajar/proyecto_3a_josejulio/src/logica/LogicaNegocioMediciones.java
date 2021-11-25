package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Medicion;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.MedicionController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.User;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.gson.Gson;

public class LogicaNegocioMediciones {
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";


    public interface MedicionesCallback{
        void onCompletedObtenerMediciones(MedicionController medicionController);
        void onFailedObtenerMediciones(boolean res);
    }

    public LogicaNegocioMediciones(){

    }

    public void obtenerMediciones(MedicionesCallback medicionesCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();


        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/mediciones", null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                try {
                    Gson gson= new Gson();
                    MedicionController medicionController= gson.fromJson(cuerpo, MedicionController.class);
                    Log.d("pepe", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + medicionController);

                    //comprobamos si esta registrado en nuestra bbdd o no
                    float success= medicionController.getSuccess();
                    if(success == 1.0){
                        medicionesCallback.onCompletedObtenerMediciones(medicionController);
                    }else if(success == 0.0){
                        medicionesCallback.onFailedObtenerMediciones(true);

                    }
                }catch (Exception e){
                    Log.d("Error", " Error");
                }
            }
        });
    }
}