package com.kevin.prayerappservice.controllers;

import com.kevin.prayerappservice.entities.Role;
import com.kevin.prayerappservice.entities.User;
import com.kevin.prayerappservice.entities.UserEmail;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.models.CreateUserRequest;
import com.kevin.prayerappservice.models.UserDetails;
import com.kevin.prayerappservice.repositories.UserEmailRepository;
import com.kevin.prayerappservice.repositories.UserRepository;
import com.kevin.prayerappservice.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager {
    private final UserRepository userRepository;
    private final UserEmailRepository userEmailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserManager(UserRepository userRepository, UserEmailRepository userEmailRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userEmailRepository = userEmailRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public UserDetails createUser(CreateUserRequest request){
        List<UserEmail> existingUserEmails =  userEmailRepository.findAllByEmail(request.getEmail());
        if(!existingUserEmails.isEmpty()){
            throw new DataValidationException(new String[]{"Email must be unique to each user."});
        }

        User user = new User(request.getFullName(), request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        UserEmail userEmail = new UserEmail(user, request.getEmail());
        user.setUserEmail(userEmail);
        userRepository.save(user);

        return null;
    }

}
