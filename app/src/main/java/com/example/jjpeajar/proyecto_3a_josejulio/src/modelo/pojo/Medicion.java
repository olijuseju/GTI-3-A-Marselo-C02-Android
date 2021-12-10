package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Andrey Kuzmin
 * Medicion
 * 2021-10-14
 */

public class Medicion {
    public int id;
    public int user_id;
    public int device_id;
    public double latitude;
    public double longitude;
    public String type_read;
    public double value;
    public int date;

    public Medicion(){}

    public Medicion(int id, int user_id, int device_id, double latitude, double longitude, String type_read, double value, int date) {
        this.id = id;
        this.user_id = user_id;
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type_read = type_read;
        this.value = value;
        this.date = date;
    }

    public Medicion (int user_id, int device_id, double latitude, double longitude, String type_read, double value, int date) {
        this.user_id = user_id;
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type_read = type_read;
        this.value = value;
        this.date = date;
    }

    //constructor para guardar medicion (sin date ya que la bd se lo asigna automatico)
    public Medicion (int user_id, int device_id, double latitude, double longitude, String type_read, double value) {
        this.user_id = user_id;
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type_read = type_read;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType_read() {
        return type_read;
    }

    public void setType_read(String type_read) {
        this.type_read = type_read;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String toJSON(){
        //return "{\"data\": "+this.data+"\", \"read_date\": "+this.read_date+"\", \"device_id\": "+this.deviceId+"\"}";
        return "user_id="+this.user_id+"&device_id="+this.device_id+"&latitude="+this.latitude+"&longitude="+this.longitude+"&type_read="+this.type_read+"&value="+this.value+"&date="+this.date;
    }
}
