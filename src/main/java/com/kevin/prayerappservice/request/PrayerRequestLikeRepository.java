package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrayerRequestLikeRepository extends JpaRepository<PrayerRequestLike, Integer> {
}
