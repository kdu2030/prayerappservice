package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrayerGroupUserRepository extends JpaRepository<PrayerGroupUser, Integer> {
    Optional<PrayerGroupUser> findByPrayerGroup_prayerGroupIdAndUser_userId(int prayerGroupId, int userId);
}
