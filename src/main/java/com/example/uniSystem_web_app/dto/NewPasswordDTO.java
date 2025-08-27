package com.example.uniSystem_web_app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 4 , max = 16 , message = "Password must be 4-16 characters.")
    String newPassword;

    @NotEmpty(message = "Confirm your password.")
    private String rePassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
