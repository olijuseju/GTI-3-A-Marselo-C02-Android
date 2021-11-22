package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.content.Context;
import android.content.Intent;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.User;

public class LogicaNegocioUsarios {

    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public LogicaNegocioUsarios(){ }

    public void guardarUsuario(User user , Context context){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/user/create", user.toJSON(), new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);

                Intent i = new Intent();
                i.putExtra("respuestaCrearUsario", cuerpo);
                context.sendBroadcast(i);

            }
        });
    }

    public void obtenerUsario(int idUser, Context context){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/user/"+idUser, null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);

                Intent i = new Intent();
                i.putExtra("obtenerUsario", cuerpo);
                context.sendBroadcast(i);

            }
        });
    }
}
