package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;


/**
 * @author Jose Julio Peñaranda
 * UserAccountInformation
 * 2021-10-23
 */


public class UserAccountInformation {
    public int id;
    public int user_id;
    public int email_verified;
    public String access_token;
    public String created_at;
    public String updated_at;

    //constructor
    public UserAccountInformation(){}

    public UserAccountInformation(int id, int user_id, int email_verified, String access_token, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.email_verified = email_verified;
        this.access_token = access_token;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    //getters && setters
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

    public int getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(int email_verified) {
        this.email_verified = email_verified;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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
