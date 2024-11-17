package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PrayerGroupController implements PrayerGroupApi {

    @Override
    public ResponseEntity<Void> createNewPrayerGroup(NewPrayerGroup group) {
        return null;
    }
}
