package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

public class User {

    //atributos
    private int Id;
    private String Name;
    private String Email;
    private String Password;
    //tabla userinformation
    private int Rol_id;
    private int Dispositivo_id;
    private int Municipio_id;
    //tabla useraccountinformation
    private String Access_token;

    //constructor por defecto
    public User() {}

    //constructor con parametros para obtener todos sus datos
    public User(int id, String name, String email, String password, int rol_id, int dispositivo_id, int municipio_id, String access_token) {
        Id = id;
        Name = name;
        Email = email;
        Password = password;
        Rol_id = rol_id;
        Dispositivo_id = dispositivo_id;
        Municipio_id = municipio_id;
        Access_token = access_token;
    }

    //constructor para crear usuarios
    public User(String name, String email, String password, int municipio_id) {
        Name = name;
        Email = email;
        Password = password;
        Municipio_id = municipio_id;
    }

    //to JSON
    public String toJSON(){
        //return "{\"data\": "+this.data+"\", \"read_date\": "+this.read_date+"\", \"device_id\": "+this.deviceId+"\"}";
        return "name="+getName()
                +"&email="+getEmail()
                +"&password="+getPassword()
                +"&rol_id=" + getRol_id();
    }

    //Getters && Setters

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRol_id() {
        return Rol_id;
    }

    public void setRol_id(int rol_id) {
        Rol_id = rol_id;
    }

    public int getDispositivo_id() {
        return Dispositivo_id;
    }

    public void setDispositivo_id(int dispositivo_id) {
        Dispositivo_id = dispositivo_id;
    }

    public int getMunicipio_id() {
        return Municipio_id;
    }

    public void setMunicipio_id(int municipio_id) {
        Municipio_id = municipio_id;
    }

    public String getAccess_token() {
        return Access_token;
    }

    public void setAccess_token(String access_token) {
        Access_token = access_token;
    }
}
