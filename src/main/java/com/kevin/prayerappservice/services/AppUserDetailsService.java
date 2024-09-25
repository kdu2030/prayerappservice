package com.kevin.prayerappservice.services;

import com.kevin.prayerappservice.entities.UserEmail;
import com.kevin.prayerappservice.repositories.UserEmailRepository;
import com.kevin.prayerappservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kevin.prayerappservice.entities.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserEmailRepository userEmailRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findByUsername(username);
        return userResult.orElseThrow(() -> new UsernameNotFoundException(String.format("Unable to find username %s", username)));
    }

    public UserDetails loadUserByEmail(String email) {
        Optional<UserEmail> userEmailResult = userEmailRepository.findByEmail(email);
        UserEmail userEmail = userEmailResult.orElseThrow();
        return userEmail.getUser();
    }
}
