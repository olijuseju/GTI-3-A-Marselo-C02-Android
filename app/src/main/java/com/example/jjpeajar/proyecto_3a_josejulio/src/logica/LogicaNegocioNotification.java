package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Notification;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.NotificationController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Town;
import com.google.gson.Gson;

import java.util.List;

public class LogicaNegocioNotification {

    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public interface ObtenerNotificacionesByIdUserCallback {
        void onCompletedObtenerNotificacionesByIdUserCallback(List<Notification> notifications);
        void onFailedObtenerNotificacionesByIdUserCallback(boolean res);
    }

    public void obtenerNotificacionesByIdUser(String access_token , ObtenerNotificacionesByIdUserCallback obtenerNotificacionesByIdUserCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/notificaciones", null , access_token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + cuerpo+"");
                    Gson gson= new Gson();
                    NotificationController notificationController = gson.fromJson(cuerpo, NotificationController.class);

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + notificationController.getSuccess()+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    int success= notificationController.getSuccess();
                    if(success == 1){
                        obtenerNotificacionesByIdUserCallback
                                .onCompletedObtenerNotificacionesByIdUserCallback(notificationController.getNotifications());
                    }else {
                        obtenerNotificacionesByIdUserCallback.onFailedObtenerNotificacionesByIdUserCallback(true);
                    }



            }
        });
    }

}
