package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.entities.PrayerRequestComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrayerRequestCommentRepository extends JpaRepository<PrayerRequestComment, Integer> {
}
