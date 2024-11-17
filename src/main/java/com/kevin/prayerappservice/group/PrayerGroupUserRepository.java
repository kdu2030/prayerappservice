package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrayerGroupUserRepository extends JpaRepository<PrayerGroupUser, Integer> {

}
