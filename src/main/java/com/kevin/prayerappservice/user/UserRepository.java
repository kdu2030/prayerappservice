package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.entities.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(@Email String email);

    List<User> findAllByEmail(String email);
}
