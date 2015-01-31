package com.expercise.controller.user.model;

import javax.validation.Valid;

public class PasswordResetModel {

    @Valid
    private PasswordModel passwordModel;

    private String token;

    public PasswordModel getPasswordModel() {
        return passwordModel;
    }

    public void setPasswordModel(PasswordModel passwordModel) {
        this.passwordModel = passwordModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
