package com.kevin.prayerappservice.user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
public class UserEmail {
    @Id
    @GeneratedValue
    private Integer userEmailId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName="userId")
    private User user;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    public UserEmail(){
        this.user = null;
        this.email = null;
        this.userEmailId = null;
    }

    public UserEmail(User user, String email){
        this.user = user;
        this.email = email;
    }

    public Integer getUserEmailId() {
        return userEmailId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }
}
