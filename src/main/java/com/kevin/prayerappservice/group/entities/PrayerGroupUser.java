package com.kevin.prayerappservice.group.entities;

import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class PrayerGroupUser {
    @Id
    @GeneratedValue
    private Integer prayerGroupUserId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "prayer_group_id")
    private PrayerGroup prayerGroup;

    @NotNull
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

    public @NotNull PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(@NotNull PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }
}
