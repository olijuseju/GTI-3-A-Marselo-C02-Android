package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

public class Notification {
    private Integer id;
    private Integer userId;
    private String date;
    private String message;
    private String type;
    private String createdAt;
    private String updatedAt;

    //contructor vacio
    public Notification() { }

    //contructor para sacar notificaciones en un rv por ejemplo


    public Notification(String date, String message, String type) {
        this.date = date;
        this.message = message;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
