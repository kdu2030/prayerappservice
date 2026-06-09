package com.kevin.prayerappservice.request.entities;

import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
public class PrayerRequestComment {
    @Id
    @GeneratedValue
    private Integer prayerRequestCommentId;

    @Lob
    @NotNull
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String comment;

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

    public PrayerRequestComment(){}

    public PrayerRequestComment(String comment, PrayerRequest prayerRequest, User user, OffsetDateTime submittedDate) {
        this.comment = comment;
        this.prayerRequest = prayerRequest;
        this.user = user;
        this.submittedDate = submittedDate;
    }

    public Integer getPrayerRequestCommentId() {
        return prayerRequestCommentId;
    }

    public void setPrayerRequestCommentId(Integer prayerRequestCommentId) {
        this.prayerRequestCommentId = prayerRequestCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
