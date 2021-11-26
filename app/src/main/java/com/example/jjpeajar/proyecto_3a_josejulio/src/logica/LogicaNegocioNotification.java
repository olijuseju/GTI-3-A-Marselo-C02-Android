package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

/**
 * @author Andrey Kuzmin
 * LogicaFake
 * 2021-10-24
 */

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Notification;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.NotificationController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Town;
import com.google.gson.Gson;

import java.util.List;

public class LogicaNegocioNotification {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    // ObtenerNotificaciones interface
    public interface ObtenerNotificacionesByIdUserCallback {
        void onCompletedObtenerNotificacionesByIdUserCallback(List<Notification> notifications);
        void onFailedObtenerNotificacionesByIdUserCallback(boolean res);
    }
    // ObtenerNotificaciones interface
    public interface DeleteNotificacionesByIdUserCallback {
        void onCompletedDeleteNotificacionesByIdUserCallback(String message);
        void onFailedDeleteNotificacionesByIdUserCallback(boolean res);
    }

    /**
     * La descripción de obtenerNotificacionesByIdUser. Funcion que obtiene las notificaciones de la bbdd.
     *
     * @param access_token String con el token del usuario
     * @param obtenerNotificacionesByIdUserCallback Objeto ObtenerNotificacionesByIdUserCallback para poder devolver el cuerpo.
     *
     */
    public void obtenerNotificacionesByIdUser(String access_token , ObtenerNotificacionesByIdUserCallback obtenerNotificacionesByIdUserCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        Log.d("pepe", " ENTRAAAAAAAAAAA -------------------------------------  ");
        Log.d("pepe", "  CUERPO ->" + access_token+"");

        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/notificaciones/user", null , access_token , new PeticionarioRest.RespuestaREST() {
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

    /**
     * La descripción de deleteNotificacionesByIdUser. Funcion que elimina las notificaciones de un usuario.
     *
     * @param access_token String con el token del usuario
     * @param deleteNotificacionesByIdUserCallback Objeto DeleteNotificacionesByIdUserCallback para poder devolver el cuerpo.
     *
     */
    public void DeleteNotificacionesByIdUser(String access_token , DeleteNotificacionesByIdUserCallback deleteNotificacionesByIdUserCallback ){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("DELETE", ADDRESS + "/api/v1/notificaciones/user", null , access_token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + cuerpo+"");
                Gson gson= new Gson();
                NotificationController notificationController = gson.fromJson(cuerpo, NotificationController.class);

                Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + notificationController.getSuccess()+"");

                //comprobamos si esta registrado en nuestra bbdd o no
                int success= notificationController.getSuccess();
                if(success == 1){
                    deleteNotificacionesByIdUserCallback
                            .onCompletedDeleteNotificacionesByIdUserCallback(notificationController.getMessage());
                }else {
                    deleteNotificacionesByIdUserCallback.onFailedDeleteNotificacionesByIdUserCallback(true);
                }
            }
        });
    }

}
