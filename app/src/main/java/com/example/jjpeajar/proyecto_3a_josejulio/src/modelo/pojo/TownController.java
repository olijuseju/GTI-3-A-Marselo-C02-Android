package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Belén Grande
 * Town Controller
 * 2021-11-24
 *
 * Pojo que utilizamos para convertir el JSON.
 */

import java.util.ArrayList;
import java.util.List;

public class TownController {
    private List<Town> towns = new ArrayList<Town>();
    private Integer success;
    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
