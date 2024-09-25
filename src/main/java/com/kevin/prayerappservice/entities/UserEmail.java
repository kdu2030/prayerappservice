package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
