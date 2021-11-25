package com.example.jjpeajar.proyecto_3a_josejulio.src.modelo.pojo;

public class UserController {

    private User user;
    private UserAccountInformation userAccountInformation;
    private UserInformation userInformation;
    private String access_token;
    private float success;
    private String message;

    // Getter Methods
    public User getUser() {
        return user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public float getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }


    // Setter Methods
    public void setUser(User userObject) {
        this.user = userObject;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setSuccess(float success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserAccountInformation getUserAccountInformation() {
        return userAccountInformation;
    }

    public void setUserAccountInformation(UserAccountInformation userAccountInformation) {
        this.userAccountInformation = userAccountInformation;
    }
}
