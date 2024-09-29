package com.kevin.prayerappservice.controllers;

import com.kevin.prayerappservice.models.CreateUserRequest;
import com.kevin.prayerappservice.models.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController implements UserApi {
    private final UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    public ResponseEntity<UserDetails> createUser(@RequestBody @Valid CreateUserRequest request){
        UserDetails createdUserDetails = userManager.createUser(request);
        return new ResponseEntity<>(createdUserDetails, HttpStatus.OK);
    }

}
