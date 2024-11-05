package com.kevin.prayerappservice.user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
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

    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){
        this.fullName = null;
        this.username = null;
        this.passwordHash = null;
        this.role = null;
        this.userEmail = null;
    }

    public User(String fullName, String username, String email, String passwordHash, Role role){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
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

    public @NotBlank String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public @NotBlank String getFullName() {
        return fullName;
    }

    public @NotBlank String getPasswordHash() {
        return passwordHash;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setFullName(@NotBlank String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPasswordHash(@NotBlank String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }
}
