package com.kevin.prayerappservice.repositories;

import com.kevin.prayerappservice.entities.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEmailRepository extends JpaRepository<UserEmail, Integer> {
    Optional<UserEmail> findByEmail(String email);
}
