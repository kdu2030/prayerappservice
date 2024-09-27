package com.kevin.prayerappservice.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateUserRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;
}
