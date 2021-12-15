package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Andrey Kuzmin
 * Notification
 * 2021-11-24
 *
 *
 */


public class Notification {
    private Integer id;
    private String user_id;
    private String date;
    private String message;
    private String type;
    /*private String created_at;
    private String updated_at;*/

    //contructor vacio
    public Notification() { }

    //lo mismo que el anterior pero con el atributo created_at
    public Notification(String date, String message, String type, String created_at) {
        this.date = date;
        this.message = message;
        this.type = type;
        /*this.created_at = created_at;*/
    }

    //contructor para crear notificaciones
    public Notification(String user_id, String message, String type) {
        this.user_id = user_id;
        this.message = message;
        this.type = type;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
    /*public String getCreated_at() {
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
    }*/

    //concadenar atributos
    public String toJsonForCreate(){
        return  "user_id="+ getUser_id()
                +"&message="+getMessage()
                +"&type="+getType();
    }

    /*public String getConvertedDate(){
        String dbTime= getCreated_at();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long ts = dateFormat.parse("2021-11-25 21:38:58").getTime()/1000;

            return String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }*/
}
