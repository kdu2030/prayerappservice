package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class JoinRequest {
    @Id
    @GeneratedValue
    private Integer joinRequestId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "prayer_group_id")
    private PrayerGroup prayerGroup;

    @NotNull
    private LocalDate submittedDate;

    public JoinRequest(){}

    public JoinRequest(User user, PrayerGroup prayerGroup, LocalDate submittedDate) {
        this.user = user;
        this.prayerGroup = prayerGroup;
        this.submittedDate = submittedDate;
    }

    public Integer getJoinRequestId() {
        return joinRequestId;
    }

    public void setJoinRequestId(Integer joinRequestId) {
        this.joinRequestId = joinRequestId;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public @NotNull PrayerGroup getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(@NotNull PrayerGroup prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public @NotNull LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(@NotNull LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }
}
