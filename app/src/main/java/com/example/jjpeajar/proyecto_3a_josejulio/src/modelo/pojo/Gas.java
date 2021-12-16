package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

public class Gas {

    private Integer id;
    private String name;
    private String description;
    private String fix_values;
    private String information_values;
    private String alert_values;
    private String route_image;
    private String created_at;
    private String updated_at;

    //cntructor vacio
    public Gas() {}

    //constructor con all
    public Gas(Integer id, String name, String description, String fix_values, String information_values, String alert_values, String route_image, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fix_values = fix_values;
        this.information_values = information_values;
        this.alert_values = alert_values;
        this.route_image = route_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    //getters && setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getFix_values() {
        return fix_values;
    }
    public void setFix_values(String fix_values) {
        this.fix_values = fix_values;
    }
    public String getInformation_values() {
        return information_values;
    }
    public void setInformation_values(String information_values) {
        this.information_values = information_values;
    }
    public String getAlert_values() {
        return alert_values;
    }
    public void setAlert_values(String alert_values) {
        this.alert_values = alert_values;
    }
    public String getRoute_image() {
        return route_image;
    }
    public void setRoute_image(String route_image) {
        this.route_image = route_image;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
