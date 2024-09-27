package com.kevin.prayerappservice.controllers;

import com.kevin.prayerappservice.entities.Role;
import com.kevin.prayerappservice.entities.User;
import com.kevin.prayerappservice.entities.UserEmail;
import com.kevin.prayerappservice.models.CreateUserRequest;
import com.kevin.prayerappservice.models.UserTokenPair;
import com.kevin.prayerappservice.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<UserTokenPair> createUser(@RequestBody @Valid CreateUserRequest request){
        User user = new User(request.getFullName(), request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        UserEmail userEmail = new UserEmail(user, request.getEmail());
        user.setUserEmail(userEmail);
        userRepository.save(user);
        return null;
    }

}
