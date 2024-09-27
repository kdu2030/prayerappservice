package com.kevin.prayerappservice.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserTokenPair implements Serializable {
    private String authToken;
    private String refreshToken;
}
