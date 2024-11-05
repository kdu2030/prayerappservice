package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.UserService;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import com.kevin.prayerappservice.user.models.CreateUserRequest;
import com.kevin.prayerappservice.user.models.UserSummary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    @DirtiesContext
    public void createUserSummary_validUser_createsUserSummary(){
        CreateUserRequest createUserRequest = new CreateUserRequest("ct7567", "Captain Rex", "captainrex@republic.gov", "233456544");
        UserSummary userSummary = userService.createUser(createUserRequest);

        Assertions.assertThat(userSummary.getUsername()).isEqualTo("ct7567");
        Assertions.assertThat(userSummary.getFullName()).isEqualTo("Captain Rex");
        Assertions.assertThat(userSummary.getEmailAddress()).isEqualTo("captainrex@republic.gov");
    }

    @Test
    @DirtiesContext
    public void createUserSummary_duplicateEmail_throwsException(){
        User user = new User("Count Dooku", "countD00ku", "countdooku@cis.gov", "21345412", Role.USER);

        userRepository.save(user);

        CreateUserRequest duplicateEmailRequest = new CreateUserRequest("anakinSkywalker", "Anakin Skywalker", "countdooku@cis.gov", "jediMaster");
        Assertions.assertThatThrownBy(() -> userService.createUser(duplicateEmailRequest)).isInstanceOf(DataValidationException.class);
    }






}
