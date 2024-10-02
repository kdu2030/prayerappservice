package com.kevin.prayerappservice.user.models;

public class CreateUserRequest {
    private final String username;
    private final String fullName;
    private final String email;
    private final String password;

    public CreateUserRequest(String username, String fullName, String email, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

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
