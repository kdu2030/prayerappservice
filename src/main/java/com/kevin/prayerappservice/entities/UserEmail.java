package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
public class UserEmail {
    @Id
    @GeneratedValue
    private Integer userEmailId;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName="userId")
    private User user;

    @Getter
    @Column(unique = true)
    private String email;

    public UserEmail(User user, String email){
        this.user = user;
        this.email = email;
    }
}
