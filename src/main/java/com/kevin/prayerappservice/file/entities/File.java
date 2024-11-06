package com.kevin.prayerappservice.file.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class File {
    @Id
    @GeneratedValue
    private Integer fileId;

    @NotBlank
    private String fileName;
}
