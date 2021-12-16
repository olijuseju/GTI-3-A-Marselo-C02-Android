package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Belén Grande
 * Town
 * 2021-11-24
 */


public class Town {

    private Integer id;
    private String postalCode;
    private String name;
    private Integer area;
    private Integer altitude;
    private Integer o2avg;
    private Object geojson;
    private Object flagImg;
    private String createdAt;
    private String updatedAt;


    // Constructor vacío
    public Town() {

    }

    // Constructor con todos menos update y create
    public Town(Integer id, String postalCode, String name, Integer area, Integer altitude, Integer o2avg, Object geojson, Object flagImg) {
        this.id = id;
        this.postalCode = postalCode;
        this.name = name;
        this.area = area;
        this.altitude = altitude;
        this.o2avg = o2avg;
        this.geojson = geojson;
        this.flagImg = flagImg;
    }

    // Constructor para obtener municipios para elegir en select el municipio
    public Town(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //getters && setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getArea() {
        return area;
    }
    public void setArea(Integer area) {
        this.area = area;
    }
    public Integer getAltitude() {
        return altitude;
    }
    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }
    public Integer getO2avg() {
        return o2avg;
    }
    public void setO2avg(Integer o2avg) {
        this.o2avg = o2avg;
    }
    public Object getGeojson() {
        return geojson;
    }
    public void setGeojson(Object geojson) {
        this.geojson = geojson;
    }
    public Object getFlagImg() {
        return flagImg;
    }
    public void setFlagImg(Object flagImg) {
        this.flagImg = flagImg;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
