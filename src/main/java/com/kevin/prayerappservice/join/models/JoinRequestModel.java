package com.kevin.prayerappservice.join.models;

import com.kevin.prayerappservice.user.models.UserSummary;

import java.time.LocalDate;

public class JoinRequestModel {
    private int joinRequestId;
    private UserSummary user;
    private LocalDate submittedDate;
}
