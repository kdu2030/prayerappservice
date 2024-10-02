package com.kevin.prayerappservice.user.models;

public class CreateUserRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
