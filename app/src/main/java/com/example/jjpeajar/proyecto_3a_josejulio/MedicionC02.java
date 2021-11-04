package com.example.jjpeajar.proyecto_3a_josejulio;

import java.io.Serializable;

// --------------------------------------------------------------
/**
 * @author Jose Julio Peñaranda
 * 2021-10-14
 */
// --------------------------------------------------------------


public class MedicionC02 implements Serializable {
    public String SensorId;
    public int data;
    public double latitud;
    public double longitud;

    public MedicionC02() {
    }

    public MedicionC02(String sensorId, int data, double latitud, double longitud) {
        SensorId = sensorId;
        this.data = data;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String toJSON(){
        return "{\"data\": \"" +
                Integer.toString(this.data)  +
                "\", \"id_sensor\": \"" +
                this.SensorId +""+
                "\", \"longitud\": \"" +
                this.longitud +""+
                "\", \"latitud\": \"" +
                this.latitud +""+
                "\" }";
    }




    public String getSensorId() {
        return SensorId;
    }

    public void setSensorId(String sensorId) {
        SensorId = sensorId;
    }

    public double getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
