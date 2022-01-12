package com.example.jjpeajar.proyecto_3a_josejulio.src.logica;

/**
 * @author Andrey Kuzmin
 * LogicaNecocioMediciones
 * 2021-11-22
 */


import android.util.Log;

import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.DataEstacion;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.Medicion;
import com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo.MedicionController;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogicaNegocioMediciones {

    // URL
    private static final String  ADDRESS= "http://vmi621282.contaboserver.net";


    // obtener Mediciones interface
    public interface MedicionesCallback{
        void onCompletedObtenerMediciones(MedicionController medicionController);
        void onFailedObtenerMediciones(boolean res);
    }
    // publicar medicion interface
    public interface PublicarMedicionesCallback{
        void onCompletedPublicarMediciones(int success);
        void onFailedPublicarMediciones(boolean res);
    }
    // obtener medicion by id user
    public interface ObtenerMedicionesByIdUserCallback{
        void onCompletedObtenerMediasDeMedicionesDiarias(double calidadAire , double temp , double hum );
        void onCompletedObtenerMedicionesDiarias( List<Medicion> mediciones );
        void onCompletedObtenerMedicionesVacio(boolean res);
        void onFailedObtenerMedicionesByIdUser(boolean res);
    }
    // obtener medicion by id user
    public interface ObtenerMedicionesEstacionCallback{
        void onCompletedObtenerMedicionesEstacion(double calidadAire , double temp , double hum );
        void onFailedObtenerMedicionesEstacion(boolean res);
    }
    //constructor vacío
    public LogicaNegocioMediciones(){

    }

    /**
     * La descripción de obtenerMediciones. Funcion que obtiene las mediciones de la bbdd.
     *
     * @param medicionesCallback Objeto MedicionesCallback para poder devolver el cuerpo.
     *
     */
    public void obtenerMediciones(MedicionesCallback medicionesCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //peticion REST
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/mediciones", null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                try {
                    //convertir de json  a POJO
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

    /**
     * La descripción de publicarMedicion. Funcion que publica las mediciones de CO2 en la bbdd.
     * @param token token del user que ha inciado sesion
     * @param userId  id del user
     * @param sensorId  id del sensor
     * @param latitud   latitud
     * @param longitud longitud
     */
    public void publicarMedicion(String token, int userId, int sensorId, double media, double latitud, double longitud, String type_read, PublicarMedicionesCallback publicarMedicionesCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();
        //init Medicion
        Medicion medicion = new Medicion(userId, sensorId, latitud, longitud, type_read, media);
        //peticion
        peticionarioRest.realizarPeticion("POST", ADDRESS + "/api/v1/medicion/create", medicion.toJSON(),token, new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                try {
                    //convertir de json a POJO
                    Gson gson= new Gson();
                    MedicionController medicionController= gson.fromJson(cuerpo, MedicionController.class);

                    Log.d("pepe", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + medicionController.getSuccess());
                    //guardar respuesta en el interface
                    publicarMedicionesCallback.onCompletedPublicarMediciones(medicionController.getSuccess());

                }catch (Exception e){
                    Log.d("Error", "Error");
                    //error
                    publicarMedicionesCallback.onFailedPublicarMediciones(true);
                }
            }
        });
    }

    /**
     * La descripción de obtenerMedicionesByIdUser. Funcion que obtiene las medicionesde un user de la bbdd.
     *
     * @param access_token String con el token del usuario
     * @param obtenerNotificacionesByIdUserCallback Objeto ObtenerNotificacionesByIdUserCallback para poder devolver el cuerpo.
     *
     */
    public void obtenerMedicionesByIdUser(String access_token , ObtenerMedicionesByIdUserCallback obtenerNotificacionesByIdUserCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        Log.d("pepe", " ENTRAAAAAAAAAAA -------------------------------------  ");
        Log.d("pepe", "  CUERPO ->" + access_token+"");

        //peticion
        peticionarioRest.realizarPeticion("GET", ADDRESS + "/api/v1/mediciones/user", null , access_token , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {

                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                Log.d("pepe", "  CUERPO ->" + cuerpo+"");

                try{
                    //convertir de json a POJO
                    Gson gson= new Gson();
                    MedicionController medicionController = gson.fromJson(cuerpo, MedicionController.class);

                    Log.d("pepe", " RRECIBIDO -------------------------------------  ");
                    Log.d("pepe", "  CUERPO ->" + medicionController.getSuccess()+"");

                    //comprobamos si esta registrado en nuestra bbdd o no
                    int success= medicionController.getSuccess();
                    if(success == 1){

                        //conseguir la ultima medicion de cada tipo de medicion (Calidad de aire , temp y hum)
                        List<Medicion> medicionList = medicionController.getMediciones();

                        //si no esta vacio el array
                        if(medicionList.size() != 0){

                            //donde vamos a guardar la ultima medicion de cada tipo
                            //Medicion ultimaMedicionCalidadAire= new Medicion();
                            List<Medicion> medicionesCalidadAireDeHoy = new ArrayList<>();
                            //Medicion ultimaMedicionTemp= new Medicion();
                            List<Medicion> medicionesTempDeHoy = new ArrayList<>();
                            //Medicion ultimaMedicionHum= new Medicion();
                            List<Medicion> medicionesHumDeHoy = new ArrayList<>();
                            //recorremos toda la lista
                            for(Medicion medicion : medicionList){
                                //si es de un tipo almacenamos en una variable, la ultima almacenada es la ultima que se ha registrado
                                if(medicion.getType_read().equals("CO2")){

                                    //comprobamos si la medicion es de hoy llamando a un metodo
                                    if(isMedicionOfToday(medicion.getDate())){
                                        //guardamos la medicion
                                        //ultimaMedicionCalidadAire = medicion;
                                        medicionesCalidadAireDeHoy.add(medicion);
                                    }
                                }
                                if(medicion.getType_read().equals("Humedad")){

                                    if(isMedicionOfToday(medicion.getDate())){
                                        //ultimaMedicionHum = medicion;
                                        medicionesHumDeHoy.add(medicion);

                                    }
                                }
                                if(medicion.getType_read().equals("Temperatura")){
                                    if(isMedicionOfToday(medicion.getDate())){
                                        //ultimaMedicionTemp = medicion;
                                        medicionesTempDeHoy.add(medicion);
                                    }
                                }
                            }
                            //variables donde vamos a almacenar el valor de cada media de medicion
                            double calidadAire;
                            double sumatorioCalidad=0;
                            double temp;
                            double sumatorioHum=0;
                            double hum;
                            double sumatorioTemp=0;

                            //media de cada tipo de medicion de hoy

                            //-------------------------
                            //calidad de aire
                            for (Medicion medicion: medicionesCalidadAireDeHoy) {
                                sumatorioCalidad = sumatorioCalidad + medicion.getValue();
                            }
                            //media
                            calidadAire = sumatorioCalidad / medicionesCalidadAireDeHoy.size();
                            // 2 decimales
                            calidadAire=Math.round(calidadAire*100.0)/100.0;
                            //-------------------------
                            //hum
                            for (Medicion medicion: medicionesHumDeHoy) {
                                sumatorioHum = sumatorioHum + medicion.getValue();
                            }
                            //media
                            hum = sumatorioHum / medicionesHumDeHoy.size();
                            // 2 decimales
                            hum=Math.round(hum*100.0)/100.0;
                            //-------------------------
                            //temp
                            for (Medicion medicion: medicionesTempDeHoy) {
                                sumatorioTemp = sumatorioTemp + medicion.getValue();
                            }
                            //media
                            temp = sumatorioTemp / medicionesTempDeHoy.size();
                            // 2 decimales
                            temp=Math.round(temp*100.0)/100.0;

                            Log.d("pepe", "  putaaaaaaaaaaaa ->"+" " + calidadAire+" " + hum+" " + temp);

                            //devolver  medias de mediciones
                            obtenerNotificacionesByIdUserCallback
                                    .onCompletedObtenerMediasDeMedicionesDiarias(calidadAire , temp , hum);

                            // devolver el array de las mediciones diarias
                            obtenerNotificacionesByIdUserCallback
                                    .onCompletedObtenerMedicionesDiarias(medicionList);

                        }else { //si esta vacio
                            //devolver respuesta
                            obtenerNotificacionesByIdUserCallback.onCompletedObtenerMedicionesVacio(true);
                        }

                    }else {
                        obtenerNotificacionesByIdUserCallback.onFailedObtenerMedicionesByIdUser(true);
                    }
                }catch (Exception e){
                    Log.d("pepe", "  CUERPO ->"+" ERROR  ");
                }



            }
        });
    }
    /**
     * La descripción de isMedicionOfToday. Comprueba si la fecha que le pasamos es la actual
     *
     * @param fechaInicio Fecha en formato yyyy-dd-MM
     * @return boolean True si es de hoy y false si no lo es.
     *
     */
    private Boolean isMedicionOfToday( String fechaInicio){

        LocalDate dateObj = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateObj = LocalDate.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = dateObj.format(formatter);
        }
        //Log.d("pepe", " Fecha de AHORA -> " + date);

        if(fechaInicio.equals(date)){
            //Log.d("pepe", "La medicion es DE HOY -> ");
            return true;
        }else{
            //Log.d("pepe", "La medicion NO ES DE HOY -> ");
            return false;
        }
    }


    /**
     * La descripción de obtenerMedicionesEstacionOficial. Funcion que obtiene las mediciones de la estacion oficial
     *
     * @param lat latitud de la estacion
     * @param lon longitud de la estacion
     * @param obtenerMedicionesEstacionCallback Objeto ObtenerMedicionesEstacionCallback para poder devolver el cuerpo.
     *
     */
    public void obtenerMedicionesEstacionOficial(double lat, double lon, ObtenerMedicionesEstacionCallback obtenerMedicionesEstacionCallback){
        PeticionarioRest peticionarioRest = new PeticionarioRest();

        //peticion REST
        peticionarioRest.realizarPeticion("GET", "http://api.airvisual.com/v2/nearest_city?lat="+ lat+ "&lon="+ lon+ "&key=e88b27dd-b65c-4eb6-a258-4b161fa20949", null , new PeticionarioRest.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //elTexto.setText ("cdigo respuesta: " + codigo + " <-> \n" + cuerpo);
                try {
                    //convertir de json  a POJO
                    Gson gson= new Gson();
                    DataEstacion dataEstacion= gson.fromJson(cuerpo, DataEstacion.class);
                    Log.d("pepelu", "  RECIBIDO -------------------------------------  ");
                    Log.d("pepelu", "  CUERPO ->" + dataEstacion.status);

                    //comprobamos si esta registrado en nuestra bbdd o no
                    String  success= dataEstacion.status;
                    if(success.equals("success")){
                        obtenerMedicionesEstacionCallback.onCompletedObtenerMedicionesEstacion(dataEstacion.data.current.pollution.aqius,dataEstacion.data.current.weather.tp,dataEstacion.data.current.weather.hu);
                    }else{
                        obtenerMedicionesEstacionCallback.onFailedObtenerMedicionesEstacion(true);

                    }
                }catch (Exception e){
                    Log.d("Error", " Error");
                }
            }
        });

    }

}
