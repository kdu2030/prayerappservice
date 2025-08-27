package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.CreatePrayerGroupRequest;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PrayerGroupController implements PrayerGroupApi {
    private PrayerGroupService prayerGroupService;

    public PrayerGroupController(PrayerGroupService prayerGroupService){
        this.prayerGroupService = prayerGroupService;
    }

    @Override
    public ResponseEntity<PrayerGroupModel> createPrayerGroup(String authorizationHeader, CreatePrayerGroupRequest createPrayerGroupRequest){
        PrayerGroupModel prayerGroupModel = prayerGroupService.createPrayerGroup(authorizationHeader, createPrayerGroupRequest);
        return new ResponseEntity<>(prayerGroupModel, HttpStatus.OK);
    }
}
