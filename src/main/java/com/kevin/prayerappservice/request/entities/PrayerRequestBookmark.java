package com.kevin.prayerappservice.request.entities;

import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class PrayerRequestBookmark {
    @Id
    @GeneratedValue
    private Integer prayerRequestBookmarkId;

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

    public PrayerRequestBookmark(){}

    public PrayerRequestBookmark(PrayerRequest prayerRequest, User user, LocalDateTime submittedDate) {
        this.prayerRequest = prayerRequest;
        this.user = user;
        this.submittedDate = submittedDate;
    }

    public Integer getPrayerRequestBookmarkId() {
        return prayerRequestBookmarkId;
    }

    public void setPrayerRequestBookmarkId(Integer prayerRequestBookmarkId) {
        this.prayerRequestBookmarkId = prayerRequestBookmarkId;
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
