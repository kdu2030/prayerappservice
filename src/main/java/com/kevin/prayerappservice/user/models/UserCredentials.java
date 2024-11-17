package com.kevin.prayerappservice.user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCredentials {
    @Email
    private final String email;

    @NotBlank
    private final String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @Email String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
