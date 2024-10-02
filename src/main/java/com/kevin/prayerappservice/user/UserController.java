package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserCredentials;
import com.kevin.prayerappservice.user.models.UserSummary;
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

    @Override
    public ResponseEntity<UserSummary> createUser(@RequestBody @Valid CreateUserRequest request){
        UserSummary createdUserSummary = userService.createUser(request);
        return new ResponseEntity<>(createdUserSummary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserSummary> getUserSummary(@RequestBody @Valid UserCredentials userCredentials) {
        UserSummary userSummary = userService.getUserSummary(userCredentials);
        return new ResponseEntity<>(userSummary, HttpStatus.OK);
    }


}
