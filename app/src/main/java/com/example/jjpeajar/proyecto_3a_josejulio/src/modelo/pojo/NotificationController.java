package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Andrey Kuzmin
 * NotificationController
 * 2021-11-24
 */


import java.util.ArrayList;
import java.util.List;

public class NotificationController {

    private List<Notification> notifications = new ArrayList<Notification>();
    private Integer success;

    //getters && setters
    public List<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    public Integer getSuccess() {
        return success;
    }
    public void setSuccess(Integer success) {
        this.success = success;
    }

}
