package com.kevin.prayerappservice.user;

import com.kevin.prayerappservice.user.entities.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserEmailRepository extends JpaRepository<UserEmail, Integer> {
    Optional<UserEmail> findByEmail(String email);

    List<UserEmail> findAllByEmail(String email);
}
