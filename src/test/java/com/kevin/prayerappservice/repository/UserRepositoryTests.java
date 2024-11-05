package com.kevin.prayerappservice.repository;

import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    public UserRepository userRepository;

    @Test
    @DirtiesContext
    public void save_allUserFieldsFilled_ReturnsCreatedUser(){
        User user = new User("Dwight Schrute", "dShrute", "dshrute@dundermifflin.com", "wfeij4a2331", Role.USER);
        User createdUser = userRepository.save(user);

        Assertions.assertThat(createdUser).isNotNull();
        Assertions.assertThat(createdUser.getUserId()).isGreaterThan(0);
    }

    @Test
    @DirtiesContext
    public void save_usersWithSameUsername_ThrowsException(){
        User user = new User("Dwight Shrute", "dShrute", "dshrute@dundermifflin.com", "wer1212322", Role.USER);
        User duplicateUser = new User("Jim Halpert", "dShrute", "jhalpert@dundermifflin.com", "1232243421", Role.USER);

        userRepository.save(user);
        Assertions.assertThatThrownBy(() -> userRepository.save(duplicateUser)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DirtiesContext
    public void findByUsername_userExists_ReturnsUser(){
        User user = new User("Michael Scott", "michaelScarn", "mscarn@dundermifflin.com", "234455561df", Role.USER);
        userRepository.save(user);

        Optional<User> userResult = userRepository.findByUsername("michaelScarn");
        Assertions.assertThat(userResult.isPresent()).isTrue();

    }

}
