package com.kevin.prayerappservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue
    private Integer userId;

    @Column (length = 255)
    private String fullName;

    @Column(length = 255, unique=true)
    private String username;

    @Column(length=255, unique=true)
    private String email;

    @Column(length=255)
    private String passwordHash;
}
