package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

public class User {

    private float id;
    private String name;
    private String email;
    private String created_at;
    private String updated_at;

    //constructor por defecto
    public User() {}

    //constructor para iniciar sesion
    public User(String email) {
        this.email = email;
    }


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String toJsonWithPassword(String password){
        String mail= getEmail();
        return "email="+mail+"&password="+password;
    }
}
