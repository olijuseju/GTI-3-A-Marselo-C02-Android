package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.content.Context;
import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.User;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserInformation;
import com.google.gson.Gson;

public class LogicaNegocioUsarios {

    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    public LogicaNegocioUsarios(){ }

    //user interface
    public interface IniciarSesionCallback {
        void onCompletedIniciarSesion(UserController userController);
        void onFailedIniciarSesion(boolean res);

    }

    //user interface
    public interface GetUsuariosCallback {
        void onCompletedGetUsuario(UserController userController);
        void onFailedGetUsuario(boolean res);

    }

    public interface RegistroCallback{
        void onCompletedRegistrarUsario(int success);
        void onFailedRegistrarUsario(boolean resultado);
    }

    public interface UpdateCallback{

        void onCompletedUpdateUsuario(UserController userController);
        void onFailedUpdateUsuario(boolean resultado);
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

    public int obtenerUsario(int idUser, String token, GetUsuariosCallback getUsuariosCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/user/"+idUser, null , token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                Log.d("pepeupdate", "  CUERPO ->" + cuerpo+"");

                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                Gson gson= new Gson();
                UserController userController= gson.fromJson(cuerpo, UserController.class);


                //comprobamos si esta registrado en nuestra bbdd o no
                float success= userController.getSuccess();
                if(success == 1.0){
                    getUsuariosCallback.onCompletedGetUsuario(userController);
                }else if(success == 0.0){
                    getUsuariosCallback.onFailedGetUsuario(true);
                }


            }
        });
        return 1;
    }

    public void login(String mail, String password, IniciarSesionCallback iniciarSesionCallback){
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
                        iniciarSesionCallback.onCompletedIniciarSesion(userController);
                    }else if(success == 0.0){
                        iniciarSesionCallback.onFailedIniciarSesion(true);
                    }
                }catch (Exception e){
                    Log.d("Error", "Error");
                }

            }
        });
    }

    public void registrarUsario(String username, String correo , String password , String confirm_password , int town , int role , RegistroCallback registroCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        User user = new User(username , correo);
        String res= user.toJsonToRegister(password, confirm_password , role , town );

        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/registroapp", res , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                try {
                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + cuerpo+"");
                    Log.d("pepe", "  nnnjnk ->" + codigo+"");
                    Gson gson= new Gson();
                    UserController userController= gson.fromJson(cuerpo, UserController.class);
                    //comprobamos si esta registrado en nuestra bbdd o no
                    float success= userController.getSuccess();

                    if(success == 1.0){
                        registroCallback.onCompletedRegistrarUsario((int) success);
                    }else{
                        registroCallback.onFailedRegistrarUsario(true);
                    }
                }catch (Exception e){
                    Log.d("Error", "Error");
                }

            }
        });
    }


    public void actualizarUsuario(int id, String token, String username, String correo , String password ,int town ,UpdateCallback usariosCallback) {
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        User user = new User(username, correo);
        UserInformation userInformation = new UserInformation(id, town);
        String res = user.toJsonToUpdate(password, town);

        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/user/update/" + id, res,token, new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                try {

                    Gson gson= new Gson();
                    UserController userController= gson.fromJson(cuerpo, UserController.class);

                    Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepeupdate", "  CUERPO ->" + userController.getSuccess()+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    float success= userController.getSuccess();
                    if(success == 1.0){
                        usariosCallback.onCompletedUpdateUsuario(userController);
                    }else if(success == 0.0){
                        usariosCallback.onFailedUpdateUsuario(true);
                    }

                } catch (Exception e) {
                    Log.d("Error", "Error");
                }

            }
        });
    }
}
