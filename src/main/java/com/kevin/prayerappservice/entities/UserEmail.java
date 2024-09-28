package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Data
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

    public UserEmail(User user, String email){
        this.user = user;
        this.email = email;
    }
}
