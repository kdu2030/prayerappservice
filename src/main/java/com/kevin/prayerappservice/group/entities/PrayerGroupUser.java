package com.kevin.prayerappservice.group.entities;

import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class PrayerGroupUser {
    @Id
    @GeneratedValue
    private Integer prayerGroupUserId;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "prayer_group_id")
    private PrayerGroup prayerGroup;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private PrayerGroupRole prayerGroupRole;

    public PrayerGroupUser(){}

    public PrayerGroupUser(User user, PrayerGroup prayerGroup, PrayerGroupRole prayerGroupRole) {
        this.user = user;
        this.prayerGroup = prayerGroup;
        this.prayerGroupRole = prayerGroupRole;
    }

    public Integer getPrayerGroupUserId() {
        return prayerGroupUserId;
    }

    public void setPrayerGroupUserId(Integer prayerGroupUserId) {
        this.prayerGroupUserId = prayerGroupUserId;
    }

    public @NotBlank User getUser() {
        return user;
    }

    public void setUser(@NotBlank User user) {
        this.user = user;
    }

    public @NotBlank PrayerGroup getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(@NotBlank PrayerGroup prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public @NotBlank PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(@NotBlank PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
