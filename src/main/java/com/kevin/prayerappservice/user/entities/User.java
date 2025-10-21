package com.kevin.prayerappservice.user.entities;

import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.join.entities.JoinRequest;
import com.kevin.prayerappservice.request.entities.PrayerRequest;
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

    @ManyToOne
    @JoinColumn(name = "image_file_id")
    private MediaFile imageFile;

    @OneToMany(mappedBy = "user")
    private List<PrayerGroupUser> prayerGroupUsers;

    @OneToMany(mappedBy = "user")
    private List<JoinRequest> joinRequests;

    @OneToMany(mappedBy = "user")
    private List<PrayerRequest> prayerRequests;

    public User(){
        this.fullName = null;
        this.username = null;
        this.passwordHash = null;
        this.role = null;
        this.email = null;
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

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public MediaFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MediaFile imageFile) {
        this.imageFile = imageFile;
    }

    public List<PrayerGroupUser> getPrayerGroupUsers() {
        return prayerGroupUsers;
    }

    public void setPrayerGroupUsers(List<PrayerGroupUser> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }

    public List<JoinRequest> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(List<JoinRequest> joinRequests) {
        this.joinRequests = joinRequests;
    }

    public List<PrayerRequest> getPrayerRequests() {
        return prayerRequests;
    }

    public void setPrayerRequests(List<PrayerRequest> prayerRequests) {
        this.prayerRequests = prayerRequests;
    }
}
