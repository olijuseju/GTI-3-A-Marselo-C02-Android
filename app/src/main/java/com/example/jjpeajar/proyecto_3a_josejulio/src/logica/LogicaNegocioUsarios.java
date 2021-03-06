package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

/**
 * @author Andrey Kuzmin
 * LogicaNecocioUsuarios
 * 2021-11-22
 */

import android.content.Context;
import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.User;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserController;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserInformation;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.UserInformationController;
import com.google.gson.Gson;

public class LogicaNegocioUsarios {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";
    //constructor
    public LogicaNegocioUsarios(){ }

    //iniciar sesion interface
    public interface IniciarSesionCallback {
        void onCompletedIniciarSesion(UserController userController);
        void onFailedIniciarSesion(boolean res);

    }

    //user interface
    public interface GetUsuariosCallback {
        void onCompletedGetUsuario(UserController userController);
        void onFailedGetUsuario(boolean res);

    }

    //registro interface
    public interface RegistroCallback{
        void onCompletedRegistrarUsario(int success);
        void onFailedRegistrarUsario(boolean resultado);
    }

    //update interface
    public interface UpdateCallback{

        void onCompletedUpdateUsuario(UserController userController);
        void onFailedUpdateUsuario(boolean resultado);
    }

    //vincular dispositivo interface
    public interface VinculateDeviceCallback {

        void onCompletedVinculateDevice(String serial, int device_id);
        void onFailedVinculateDevice(boolean resultado);
    }

    //cerrar sesion interface
    public interface CerrarSesionCallback {

        void onCompletedCerrarSesion(String message);
        void onFailedCerrarSesion(boolean resultado);
    }

    //obtener user activity
    public interface CrearActividadUsarioCallback {

        void onCompletedObtenerActividadUsario(String message);
        void onFailedObtenerActividadUsario(boolean resultado);
    }


    /**
     * La descripci??n de guardarUsuario. Funcion que guarda usuarios.
     *
     * @param username String con el nombre de usuario
     * @param mail String con el mail del usuario
     * @param password String con la contrase??a del usuario
     * @param town String con el municipio del usuario
     * @param context String con el contexto
     *
     */
    public void guardarUsuario(String username, String mail, String password, int town, Context context){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //ser user = new User(username,mail,password, town);

        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/user/create", "user.toJSON()", new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("c??digo respuesta: " + codigo + " <-> \n" + cuerpo);

                Log.d("pepe", "  Codigo -----> "+codigo);

            }
        });
    }

    /**
     * La descripci??n de obtenerUsario. Funcion que obtiene el usuario de la bbdd.
     *
     * @param idUser String con el nombre id del usuario
     * @param token String con el token para usarse en las coockies
     * @param getUsuariosCallback GetUsuariosCallback para almacenar el cuerpo
     *
     * @return int
     *
     */
    public int obtenerUsario(int idUser, String token, GetUsuariosCallback getUsuariosCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //peticion
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/user/"+idUser, null , token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                Log.d("pepeupdate", "  CUERPO ->" + cuerpo+"");

                //elTexto.setText ("c??digo respuesta: " + codigo + " <-> \n" + cuerpo);
                //convertir json a POJO
                Gson gson= new Gson();
                UserController userController= gson.fromJson(cuerpo, UserController.class);


                //comprobamos si esta registrado en nuestra bbdd o no
                float success= userController.getSuccess();
                if(success == 1.0){
                    //le pasamos al callback el valor
                    getUsuariosCallback.onCompletedGetUsuario(userController);
                }else if(success == 0.0){
                    getUsuariosCallback.onFailedGetUsuario(true);
                }


            }
        });
        return 1;
    }

    /**
     * La descripci??n de login. Funcion que permite loguearte con un usuario almacenado en la bbdd.
     *
     * @param mail String con el mail del usuario
     * @param password String con el password del usuario
     * @param iniciarSesionCallback IniciarSesionCallback para almacenar el cuerpo
     *
     */
    public void login(String mail, String password, IniciarSesionCallback iniciarSesionCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();
        //init User POJO
        User user = new User(mail);
        //concadenar los atributos para el body de la peticion
        String res= user.toJsonWithPassword(password);
        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/login", res , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("c??digo respuesta: " + codigo + " <-> \n" + cuerpo);
                try {
                    //convertir Json a POJO
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

    /**
     * La descripci??n de registrarUsario. Funcion que permite registrar usuarios en la bbdd.
     *
     * @param username String con el nombre del usuario
     * @param correo String con el mail del usuario
     * @param password String con el password del usuario
     * @param confirm_password String con la password reconfirmada del usuario
     * @param town Int con el id del municipio
     * @param role Int con el rol del usuario
     * @param registroCallback RegistroCallback para almacenar la respuesta del cuerpo
     */
    public void registrarUsario(String username, String correo , String password , String confirm_password , int town , int role , RegistroCallback registroCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();
        //init User POJO
        User user = new User(username , correo);
        //concadenar atributos para el body de la peticion
        String res= user.toJsonToRegister(password, confirm_password , role , town );
        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/registroapp", res , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                try {
                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + cuerpo+"");
                    Log.d("pepe", "  nnnjnk ->" + codigo+"");
                    //convertir Json a POJO
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

    /**
     * La descripci??n de actualizarUsuario. Funcion que permite actualizar sus datos a los usuarios en la bbdd.
     *
     * @param id Int con el id del usuario
     * @param token String con el token del usuario
     * @param username String con el nombre del usuario
     * @param correo String con el mail del usuario
     * @param password String con la contrase??a del usuario
     * @param town Int con el id del municipio del usuario
     * @param usariosCallback UpdateCallback para almacenar la respuesta del cuerpo
     */
    public void actualizarUsuario(int id, String token, String username, String correo , String password ,int town, int role ,UpdateCallback usariosCallback) {
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //init user Pojo
        User user = new User(username, correo);
        UserInformation userInformation = new UserInformation(id, town);
        //concadenar atributos para el body de la peticion
        String res = user.toJsonToUpdate(password, town, role);
        Log.d("pepeupdate", res);

        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/user/update/" + id, res,token, new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                try {
                    //pasar de Json a Pojo
                    Gson gson= new Gson();
                    UserController userController= gson.fromJson(cuerpo, UserController.class);

                    Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepeupdate", "  CUERPO ->" + token+"");

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

    /**
    * La descripci??n de vincularDispoitivo. Funcion que permite actualizar sus datos a los usuarios en la bbdd.
    *
    * @param serial Int con el serial del dispositivo
    * @param vinculateDeviceCallback VinculateDeviceCallback para almacenar la respuesta del cuerpo
     */
    public void vincularDispoitivo(String serial, String acces_token, VinculateDeviceCallback vinculateDeviceCallback) {

        PeticionarioRest peticionarioRest = new PeticionarioRest();
        //init userInformation POJO
        UserInformation userInformation = new UserInformation();
        //concadenar atributos para el body
        String res = userInformation.SerialToJson(serial);
        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/user/device" , res , acces_token, new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                try {
                    // JSON -> UserInformationController
                    Gson gson = new Gson();
                    UserInformationController userInformationController = gson.fromJson(cuerpo, UserInformationController.class);
                    Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepeupdate", "  CUERPO ->" + cuerpo + "");
                    Log.d("pepeupdate", "  codigo ->" + codigo + "");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    int success = userInformationController.getSuccess();
                    if (success == 1) {
                        vinculateDeviceCallback.onCompletedVinculateDevice(String.valueOf(serial), userInformationController.getUserInformation().getDevice_id());
                    } else if (success == 0) {
                        vinculateDeviceCallback.onFailedVinculateDevice(true);
                    }
                }catch (Exception e){
                    Log.d("Error", "Error");
                    vinculateDeviceCallback.onFailedVinculateDevice(true);
                }


            }
        });
    }

    /**
     * La descripci??n de CerrarSesion. Funcion que cierra sesion del user , cambiando el access_token
     * la base de datos
     *
     * @param access_token String , es el access token que tiene el user que utilizamos para saber si ha iniciado sesion
     * @param cerrarSesionCallback CerrarSesionCallback para almacenar lo que devuelve el callback de la consulta
     */
    public void CerrarSesion(String access_token, CerrarSesionCallback cerrarSesionCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();
        //peticion GET
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/logout", null, access_token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                try {
                    //Json -> UserController
                    Gson gson= new Gson();
                    UserController userController= gson.fromJson(cuerpo, UserController.class);

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + userController.getMessage());
                    //guardar respuesta en el interface
                    cerrarSesionCallback.onCompletedCerrarSesion(userController.getMessage());

                }catch (Exception e){
                    Log.d("Error", "Error");
                    //error
                    cerrarSesionCallback.onFailedCerrarSesion(true);
                }
            }
        });
    }

    /**
     * La descripci??n de obtenerActividadUsario. Funcion que te da dengue del usuario
     *
     * @param idUser int
     * @param timeActivity int
     * @param distanceTraveled double
     * @param crearActividadUsarioCallback CrearActividadUsarioCallback para almacenar el cuerpo
     *
     * @return int
     *
     */
    public void crearActividadUsario(String access_token , int idUser, int timeActivity , double distanceTraveled, CrearActividadUsarioCallback crearActividadUsarioCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        String res = "user_id="+idUser+"&time_activity="+timeActivity+"&distance_traveled="+distanceTraveled;
        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/useractivities/create", res , access_token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                // elTexto.setText ("c??digo respuesta: " + codigo + " <-> \n" + cuerpo);
                try {

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + codigo +" cuerpo : " + cuerpo);

                    crearActividadUsarioCallback.onCompletedObtenerActividadUsario("funciona");

                }catch (Exception e){
                    crearActividadUsarioCallback.onFailedObtenerActividadUsario(true);
                    Log.d("Error", "Error");
                }

            }
        });
    }
}
