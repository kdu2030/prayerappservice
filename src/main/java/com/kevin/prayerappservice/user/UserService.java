package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.group.models.PrayerGroupSummaryModel;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserCredentials;
import com.kevin.prayerappservice.user.models.UserSummary;
import com.kevin.prayerappservice.user.models.UserTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PrayerGroupService prayerGroupService;

    private final static int ACCESS_TOKEN_VALIDITY_LENGTH_MS = 60 * 60 * 1000;
    private final static int REFRESH_TOKEN_VALIDITY_LENGTH_MS = 15 * 24 * 60 * 60 * 1000;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                       PrayerGroupService prayerGroupService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.prayerGroupService = prayerGroupService;
    }

    public UserSummary createUser(CreateUserRequest request) {
        Optional<User> userWithMatchingEmail = userRepository.findByEmail(request.getEmail());
        Optional<User> userWithMatchingUsername = userRepository.findByUsername(request.getUsername());

        if (userWithMatchingEmail.isPresent()) {
            throw new DataValidationException(new String[]{"Email must be unique to each user."});
        }

        if(userWithMatchingUsername.isPresent()){
            throw new DataValidationException(new String[]{"Username must be unique to each user."});
        }


        User user = new User(request.getFullName(), request.getUsername(), request.getEmail(),
                passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(user);

        return createUserSummary(user, new ArrayList<>());
    }

    public UserSummary getUserSummary(UserCredentials credentials) {
        Optional<User> userResult = userRepository.findByEmail(credentials.getEmail());
        if (userResult.isEmpty()) {
            throw new DataValidationException(HttpStatus.NOT_FOUND, new String[]{"A User with this email does not " +
                    "exist."});
        }

        User user = userResult.get();
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPasswordHash())) {
            throw new DataValidationException(HttpStatus.UNAUTHORIZED, new String[]{"Password is incorrect."});
        }

        List<PrayerGroupSummaryModel> prayerGroups = prayerGroupService.getPrayerGroupSummariesByUser(user.getUserId());

        return createUserSummary(user, prayerGroups);
    }

    public UserSummary getUserSummary(int userId){
        Optional<User> userResult = userRepository.findById(userId);

        if(userResult.isEmpty()){
            String errorMessage = String.format("A user with userId %d does not exist", userId);
            throw new DataValidationException(HttpStatus.NOT_FOUND, new String[] { errorMessage });
        }

        User user = userResult.get();
        List<PrayerGroupSummaryModel> prayerGroups = prayerGroupService.getPrayerGroupSummariesByUser(user.getUserId());
        return createUserSummary(user, prayerGroups);
    }

    public UserTokenPair getUserTokenPair(String authorization) {
        String refreshToken = jwtService.extractTokenFromAuthHeader(authorization);
        String username = jwtService.extractUsername(refreshToken);
        Integer userId = jwtService.extractUserId(refreshToken);

        User user = new User();
        user.setUsername(username);
        user.setUserId(userId);

        return generateUserTokenPair(user);
    }

    private UserTokenPair generateUserTokenPair(User user) {
        String accessToken = jwtService.generateToken(user.getUserId(), user, ACCESS_TOKEN_VALIDITY_LENGTH_MS);
        String refreshToken = jwtService.generateToken(user.getUserId(), user, REFRESH_TOKEN_VALIDITY_LENGTH_MS);
        return new UserTokenPair(accessToken, refreshToken);
    }


    private UserSummary createUserSummary(User user, List<PrayerGroupSummaryModel> prayerGroups) {
        UserTokenPair userTokenPair = generateUserTokenPair(user);

        return new UserSummary(user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                userTokenPair, null, prayerGroups);
    }

}
