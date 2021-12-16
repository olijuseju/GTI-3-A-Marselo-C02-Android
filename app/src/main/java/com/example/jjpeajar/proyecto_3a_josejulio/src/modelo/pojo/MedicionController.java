package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;


/**
 * @author Andrey Kuzmin
 * MedicionController
 * 2021-10-14
 *
 *
 * Pojo que utilizamos para convertir JSON en esta clase.
 */


import java.util.List;

public class MedicionController {
    public List<Medicion> mediciones;
    public int success;
    public String message;

    //contructor
    public List<Medicion> getMediciones() {
        return mediciones;
    }

    //getters && setters

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
