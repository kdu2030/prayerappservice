package com.kevin.prayerappservice.group.entities;

import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class PrayerGroupUser {

    @Id
    @GeneratedValue
    private Integer prayerGroupUserId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PrayerGroupRole prayerGroupRole;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "prayer_group_id")
    private PrayerGroup prayerGroup;

    public PrayerGroupUser(PrayerGroupRole prayerGroupRole, User user, PrayerGroup prayerGroup) {
        this.prayerGroupRole = prayerGroupRole;
        this.user = user;
        this.prayerGroup = prayerGroup;
    }

    public Integer getPrayerGroupUserId() {
        return prayerGroupUserId;
    }

    public @NotNull PrayerGroupRole getPrayerGroupRole() {
        return prayerGroupRole;
    }

    public void setPrayerGroupRole(@NotNull PrayerGroupRole prayerGroupRole) {
        this.prayerGroupRole = prayerGroupRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PrayerGroup getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(PrayerGroup prayerGroup) {
        this.prayerGroup = prayerGroup;
    }
}
