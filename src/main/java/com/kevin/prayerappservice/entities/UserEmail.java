package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor
public class UserEmail {
    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName="userId")
    private User user;

    @Getter
    @Column(unique = true)
    private String email;
}
