package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

import java.util.Date;

public class Device{
    public int id;
    public String serial;
    public double delta;
    public Date created_at;
    public Date updated_at;

    public Device(String serial, double delta) {
        this.serial = serial;
        this.delta = delta;
    }

    public Device(String serial) {
        this.serial = serial;
        this.delta = 0;
    }


    public Device(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String toJsonToUpdate(String serial , double delta){
        return  "serial="+serial
                +"&delta="+delta;
    }

}


