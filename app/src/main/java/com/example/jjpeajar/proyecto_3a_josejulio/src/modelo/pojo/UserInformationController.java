package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

/**
 * @author Jose Julio Peñaranda
 * UserInformation
 * 2021-10-23
 *
 * Pojo que utilizamos para convertir el JSON.
 */

public class UserInformationController {

    private String message;
    private UserInformation userInformation;
    private Integer success;

    // Constructores
    public UserInformationController() {
    }

    // Getter and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
