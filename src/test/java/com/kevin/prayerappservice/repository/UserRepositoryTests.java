package com.kevin.prayerappservice.repository;

import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    public UserRepository userRepository;

    @Test
    public void save_allUserFieldsFilled_ReturnsCreatedUser(){
        User user = new User("Dwight Schrute", "dShrute", "wfeij4a2331", Role.USER);
        User createdUser = userRepository.save(user);

        Assertions.assertThat(createdUser).isNotNull();
        Assertions.assertThat(createdUser.getUserId()).isGreaterThan(0);
    }

}
