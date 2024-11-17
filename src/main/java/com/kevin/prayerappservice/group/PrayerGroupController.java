package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PrayerGroupController implements PrayerGroupApi {

    @Override
    public ResponseEntity<Void> createPrayerGroup(@Valid @RequestBody NewPrayerGroup group) {
        return null;
    }
}
