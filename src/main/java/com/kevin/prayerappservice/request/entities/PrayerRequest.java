package com.kevin.prayerappservice.request.entities;

import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PrayerRequest {
    @Id
    @GeneratedValue
    private Integer prayerRequestId;

    @NotBlank
    private String requestTitle;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String requestDescription;

    @NotNull
    private LocalDateTime createdDate;

    private int likeCount;

    private int commentCount;

    private int prayedCount;

    private LocalDateTime expirationDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "prayer_group_id")
    private PrayerGroup prayerGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "prayerRequest")
    private List<PrayerRequestLike> prayerRequestLikes;

    public PrayerRequest(){}

    public PrayerRequest(String requestTitle, String requestDescription, LocalDateTime createdDate, int likeCount, int commentCount, int prayedCount, LocalDateTime expirationDate, PrayerGroup prayerGroup, User user) {
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.prayedCount = prayedCount;
        this.expirationDate = expirationDate;
        this.prayerGroup = prayerGroup;
        this.user = user;
    }

    public Integer getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(Integer prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPrayedCount() {
        return prayedCount;
    }

    public void setPrayedCount(int prayedCount) {
        this.prayedCount = prayedCount;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public PrayerGroup getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(PrayerGroup prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PrayerRequestLike> getPrayerRequestLikes() {
        return prayerRequestLikes;
    }

    public void setPrayerRequestLikes(List<PrayerRequestLike> prayerRequestLikes) {
        this.prayerRequestLikes = prayerRequestLikes;
    }
}
