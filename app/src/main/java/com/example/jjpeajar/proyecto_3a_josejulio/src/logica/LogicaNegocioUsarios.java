package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.User;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LogicaNegocioUsarios {

    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public LogicaNegocioUsarios(){ }

    //user interface
    public interface UsariosCallback{
        void onCompletedIniciarSesion(UserController userController);
        void onFailedIniciarSesion(boolean res);

    }

    public void guardarUsuario(String username, String mail, String password, int town, Context context){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //ser user = new User(username,mail,password, town);

        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/user/create", "user.toJSON()", new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);

                Log.d("pepe", "  Codigo -----> "+codigo);

            }
        });
    }

    public int obtenerUsario(int idUser, Context context){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/user/"+idUser, null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);



            }
        });
        return 1;
    }

    public void login(String mail, String password, UsariosCallback usariosCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        User user = new User(mail);
        String res= user.toJsonWithPassword(password);

        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/login", res , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                try {
                    Gson gson= new Gson();
                    UserController userController= gson.fromJson(cuerpo, UserController.class);

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + userController.getSuccess()+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    float success= userController.getSuccess();
                    if(success == 1.0){
                        usariosCallback.onCompletedIniciarSesion(userController);
                    }else if(success == 0.0){
                        usariosCallback.onFailedIniciarSesion(true);
                    }
                }catch (Exception e){
                    Log.d("Error", "Error");
                }

            }
        });
    }
}
