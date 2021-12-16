package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

import java.util.ArrayList;
import java.util.List;

public class GasController {

    private List<Gas> gases = new ArrayList<Gas>();
    private Integer success;

    //constructor vacio
    public GasController() {
    }

    // getters && setters
    public List<Gas> getGases() {
        return gases;
    }
    public void setGases(List<Gas> gases) {
        this.gases = gases;
    }
    public Integer getSuccess() {
        return success;
    }
    public void setSuccess(Integer success) {
        this.success = success;
    }

}
