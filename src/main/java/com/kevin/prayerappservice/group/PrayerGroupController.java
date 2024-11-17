package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import com.kevin.prayerappservice.group.models.PrayerGroupSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PrayerGroupController implements PrayerGroupApi {
    private PrayerGroupService prayerGroupService;

    @Autowired
    public PrayerGroupController(PrayerGroupService prayerGroupService){
        this.prayerGroupService = prayerGroupService;
    }

    @Override
    public ResponseEntity<PrayerGroupSummary> createPrayerGroup(String authorization, NewPrayerGroup group) {
        PrayerGroupSummary prayerGroupSummary = prayerGroupService.createPrayerGroup(authorization, group);
        return new ResponseEntity<>(prayerGroupSummary, HttpStatus.OK);
    }
}
