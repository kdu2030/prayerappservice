package com.kevin.prayerappservice.controllers;

import com.kevin.prayerappservice.entities.Role;
import com.kevin.prayerappservice.entities.User;
import com.kevin.prayerappservice.entities.UserEmail;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.models.CreateUserRequest;
import com.kevin.prayerappservice.models.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController implements UserApi {
    private final UserManager userManager;

    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    public ResponseEntity<UserDetails> createUser(@RequestBody @Valid CreateUserRequest request){
        UserDetails createdUserDetails = userManager.createUser(request);
        return new ResponseEntity<>(createdUserDetails, HttpStatus.OK);
    }

}
