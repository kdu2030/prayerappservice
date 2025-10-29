package com.kevin.prayerappservice.session.entities;

import com.kevin.prayerappservice.request.entities.PrayerRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class PrayerSession {
    @Id
    @GeneratedValue
    private Integer prayerSessionId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
            name = "prayer_request_session",
            joinColumns = { @JoinColumn(name = "prayer_session_id")},
            inverseJoinColumns = { @JoinColumn(name = "prayer_request_id")}
    )
    private Set<PrayerRequest> prayerRequests;

    public PrayerSession(){}

    public PrayerSession(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getPrayerSessionId() {
        return prayerSessionId;
    }

    public void setPrayerSessionId(Integer prayerSessionId) {
        this.prayerSessionId = prayerSessionId;
    }

    public @NotNull LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotNull LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public @NotNull LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotNull LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<PrayerRequest> getPrayerRequests() {
        return prayerRequests;
    }

    public void setPrayerRequests(Set<PrayerRequest> prayerRequests) {
        this.prayerRequests = prayerRequests;
    }
}
