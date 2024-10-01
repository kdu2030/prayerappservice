package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController implements UserApi {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserDetails> createUser(@RequestBody @Valid CreateUserRequest request){
        UserDetails createdUserDetails = userService.createUser(request);
        return new ResponseEntity<>(createdUserDetails, HttpStatus.OK);
    }

}
