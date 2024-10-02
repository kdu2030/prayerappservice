package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import com.kevin.prayerappservice.user.entities.UserEmail;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserCredentials;
import com.kevin.prayerappservice.user.models.UserSummary;
import com.kevin.prayerappservice.user.models.UserTokenPair;
import com.kevin.prayerappservice.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEmailRepository userEmailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final static int ACCESS_TOKEN_VALIDITY_LENGTH_MS = 60 * 60 * 1000;
    private final static int REFRESH_TOKEN_VALIDITY_LENGTH_MS = 15 * 24 * 60 * 60 * 1000;

    @Autowired
    public UserService(UserRepository userRepository, UserEmailRepository userEmailRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userEmailRepository = userEmailRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private UserSummary createUserSummary(User user){
        String accessToken = jwtService.generateToken(user, ACCESS_TOKEN_VALIDITY_LENGTH_MS);
        String refreshToken = jwtService.generateToken(user, REFRESH_TOKEN_VALIDITY_LENGTH_MS);
        UserTokenPair userTokenPair = new UserTokenPair(accessToken, refreshToken);

        return new UserSummary(user.getUserId(),
                user.getUserEmail().getEmail(),
                user.getFullName(),
                userTokenPair);
    }


    public UserSummary createUser(CreateUserRequest request){
        List<UserEmail> existingUserEmails =  userEmailRepository.findAllByEmail(request.getEmail());
        if(!existingUserEmails.isEmpty()){
            throw new DataValidationException(new String[]{"Email must be unique to each user."});
        }

        User user = new User(request.getFullName(), request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        UserEmail userEmail = new UserEmail(user, request.getEmail());
        user.setUserEmail(userEmail);
        userRepository.save(user);

        return createUserSummary(user);
    }

    public UserSummary getUserSummary(UserCredentials credentials){
        Optional<UserEmail> userEmail = userEmailRepository.findByEmail(credentials.getEmail());
        return null;
    }

}
