package com.kevin.prayerappservice.request.entities;

import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

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
    private OffsetDateTime submittedDate;

    public PrayerRequestBookmark(){}

    public PrayerRequestBookmark(PrayerRequest prayerRequest, User user, OffsetDateTime submittedDate) {
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

    public OffsetDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OffsetDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
