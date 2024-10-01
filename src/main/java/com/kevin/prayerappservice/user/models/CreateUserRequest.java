package com.kevin.prayerappservice.user.models;
import lombok.*;

@Data
public class CreateUserRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;
}
