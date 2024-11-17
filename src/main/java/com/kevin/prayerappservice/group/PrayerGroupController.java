package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PrayerGroupController implements PrayerGroupApi {
    private PrayerGroupService prayerGroupService;

    @Autowired
    public PrayerGroupController(PrayerGroupService prayerGroupService){
        this.prayerGroupService = prayerGroupService;
    }

    @Override
    public ResponseEntity<Void> createPrayerGroup(String authorization, NewPrayerGroup group) {
        prayerGroupService.createPrayerGroup(authorization, group);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
