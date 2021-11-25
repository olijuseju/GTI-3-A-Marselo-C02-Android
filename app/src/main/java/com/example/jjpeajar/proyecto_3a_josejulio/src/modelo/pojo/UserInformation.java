package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

import java.util.Date;

public class UserInformation {
    private int id;
    private int user_id;
    private int role_id;
    private int device_id;
    private int town_id;
    private String created_at;
    private String updated_at;

    public UserInformation(){}

    public UserInformation(int user_id) {
        this.user_id = user_id;
    }

    public UserInformation(int id, int town_id) {
        this.id = id;
        this.town_id = town_id;
    }

    public UserInformation(int id, int user_id, int role_id, int device_id, int town_id, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
        this.device_id = device_id;
        this.town_id = town_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Object getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getTown_id() {
        return town_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
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
