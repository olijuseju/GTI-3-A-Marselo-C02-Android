package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

import android.bluetooth.BluetoothClass;

public class DeviceController {


    public String message;
    public Device device;
    public int success;

    public DeviceController(String message, Device device, int success) {
        this.message = message;
        this.device = device;
        this.success = success;
    }

    public DeviceController(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
