package com.kevin.prayerappservice.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Integer> {
    Optional<JoinRequest> findByPrayerGroup_prayerGroupId(int prayerGroupId);
}
