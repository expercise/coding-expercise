package com.expercise.controller.user.model;

import com.expercise.utils.validation.PasswordMatch;

import javax.validation.constraints.Size;

@PasswordMatch
public class PasswordModel {

    @Size(min = 6, max = 16)
    private String password;

    private String passwordRetype;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRetype() {
        return passwordRetype;
    }

    public void setPasswordRetype(String passwordRetype) {
        this.passwordRetype = passwordRetype;
    }

}
