package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer userId;

    @NotBlank
    private String fullName;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEmail userEmail;

    public User(){
        this.fullName = null;
        this.username = null;
        this.passwordHash = null;
        this.role = null;
        this.userEmail = null;
    }

    public User(String fullName, String username, String passwordHash, Role role){
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

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
