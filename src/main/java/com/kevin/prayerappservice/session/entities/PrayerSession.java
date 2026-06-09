package com.kevin.prayerappservice.session.entities;

import com.kevin.prayerappservice.request.entities.PrayerRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
public class PrayerSession {
    @Id
    @GeneratedValue
    private Integer prayerSessionId;

    @NotNull
    private OffsetDateTime startTime;

    @NotNull
    private OffsetDateTime endTime;

    @ManyToMany
    @JoinTable(
            name = "prayer_request_session",
            joinColumns = { @JoinColumn(name = "prayer_session_id")},
            inverseJoinColumns = { @JoinColumn(name = "prayer_request_id")}
    )
    private Set<PrayerRequest> prayerRequests;

    public PrayerSession(){}

    public PrayerSession(OffsetDateTime startTime, OffsetDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getPrayerSessionId() {
        return prayerSessionId;
    }

    public void setPrayerSessionId(Integer prayerSessionId) {
        this.prayerSessionId = prayerSessionId;
    }

    public @NotNull OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotNull OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public @NotNull OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotNull OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<PrayerRequest> getPrayerRequests() {
        return prayerRequests;
    }

    public void setPrayerRequests(Set<PrayerRequest> prayerRequests) {
        this.prayerRequests = prayerRequests;
    }
}
