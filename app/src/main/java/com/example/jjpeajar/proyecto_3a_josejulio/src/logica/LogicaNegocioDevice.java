package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Device;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.DeviceController;
import com.google.gson.Gson;

public class LogicaNegocioDevice {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";

    //update interface
    public interface UpdateCallback{

        void onCompletedUpdateDevice(DeviceController deviceController);
        void onFailedUpdateDevice(boolean resultado);
    }

    // obtener town interface
    public interface ObtenerDeviceCallback {
        void onCompletedObtenerDevice(Device device);
        void onFailedObtenerDevice(boolean res);
    }


    /**
     *  Descripcion de actualizarDevice. Permite actualizar los datos de un sensor
     * @param id
     * @param token
     * @param serial
     * @param delta
     * @param deviceCallback
     */
    public void actualizarDevice(int id, String token, String serial, double delta, UpdateCallback deviceCallback) {
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //init user Pojo
        Device device = new Device(serial, delta);

        String res = device.toJsonToUpdate(serial, delta);
        Log.d("pepeupdate", res);

        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/dispositivos/update/" + id, res,token, new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                try {
                    //pasar de Json a Pojo
                    Gson gson= new Gson();
                    DeviceController deviceController= gson.fromJson(cuerpo, DeviceController.class);

                    Log.d("pepeupdate", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepeupdate", "  CUERPO ->" + token+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    float success= deviceController.getSuccess();
                    if(success == 1.0){
                        deviceCallback.onCompletedUpdateDevice(deviceController);
                    }else if(success == 0.0){
                        deviceCallback.onFailedUpdateDevice(true);
                    }

                } catch (Exception e) {
                    Log.d("Error actualizardevice", "Error");
                }

            }
        });
    }




    /**
     * La descripción de obtenerTown. Funcion que obtiene los municipios de la bbdd.
     *
     * @param obtenerDeviceCallback Objeto ObtenerTownsCallback para poder devolver el cuerpo.
     *
     */
    public void obtenerDevice(int id,ObtenerDeviceCallback obtenerDeviceCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //peticion
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/dispositivos/", String.valueOf(id) , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);

                try {
                    //convertir json a POJO
                    Gson gson= new Gson();
                    DeviceController deviceController= gson.fromJson(cuerpo, DeviceController.class);

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + deviceController.getSuccess()+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    int success= deviceController.getSuccess();
                    if(success == 1){
                        obtenerDeviceCallback.onCompletedObtenerDevice(deviceController.getDevice());
                        obtenerDeviceCallback.onFailedObtenerDevice(true);
                    }else {
                        obtenerDeviceCallback.onFailedObtenerDevice(false);
                    }
                }catch (Exception e){
                    Log.d("Error", "Error");
                }


            }
        });
    }

}
