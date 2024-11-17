package com.kevin.prayerappservice.group;


import com.kevin.prayerappservice.group.entities.PrayerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrayerGroupRepository extends JpaRepository<PrayerGroup, Integer> {
    Optional<PrayerGroup> findByGroupName(String groupName);
}
