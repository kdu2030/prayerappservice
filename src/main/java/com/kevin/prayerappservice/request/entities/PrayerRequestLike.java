package com.kevin.prayerappservice.request.entities;

import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class PrayerRequestLike {
    @Id
    @GeneratedValue
    private Integer prayerRequestLikeId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "prayer_request_id")
    private PrayerRequest prayerRequest;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private LocalDateTime submittedDate;

    public PrayerRequestLike(){}

    public PrayerRequestLike(LocalDateTime submittedDate, User user, PrayerRequest prayerRequest) {
        this.submittedDate = submittedDate;
        this.user = user;
        this.prayerRequest = prayerRequest;
    }

    public Integer getPrayerRequestLikeId() {
        return prayerRequestLikeId;
    }

    public void setPrayerRequestLikeId(Integer prayerRequestLikeId) {
        this.prayerRequestLikeId = prayerRequestLikeId;
    }

    public PrayerRequest getPrayerRequest() {
        return prayerRequest;
    }

    public void setPrayerRequest(PrayerRequest prayerRequest) {
        this.prayerRequest = prayerRequest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
