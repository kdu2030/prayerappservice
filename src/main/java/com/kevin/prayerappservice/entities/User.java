package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BaseUser")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer userId;

    private String fullName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEmail userEmail;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    private String getEmail() {
        return userEmail.getEmail();
    }

}
