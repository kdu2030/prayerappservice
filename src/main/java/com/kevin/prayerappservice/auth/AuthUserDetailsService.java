package com.kevin.prayerappservice.auth;

import com.kevin.prayerappservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kevin.prayerappservice.user.entities.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findByUsername(username);
        return userResult.orElseThrow(() -> new UsernameNotFoundException(String.format("Unable to find username %s", username)));
    }
}
