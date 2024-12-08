package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import com.kevin.prayerappservice.group.models.PrayerGroupSummary;
import com.kevin.prayerappservice.lang.CultureCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PrayerGroupController implements PrayerGroupApi {
    private final PrayerGroupService prayerGroupService;

    @Autowired
    public PrayerGroupController(PrayerGroupService prayerGroupService){
        this.prayerGroupService = prayerGroupService;
    }

    @Override
    public ResponseEntity<PrayerGroupSummary> createPrayerGroup(String authorization, NewPrayerGroup group) {
        PrayerGroupSummary prayerGroupSummary = prayerGroupService.createPrayerGroup(authorization, group);
        return new ResponseEntity<>(prayerGroupSummary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerGroupSummary> getPrayerGroup(@PathVariable int prayerGroupId) {
        PrayerGroupSummary prayerGroupSummary = prayerGroupService.getPrayerGroup(prayerGroupId);
        return new ResponseEntity<>(prayerGroupSummary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerGroupSummary[]> searchPrayerGroups(CultureCode cultureCodeString, String searchTerm, int userId) {
        if(searchTerm == null || userId == 0){
            throw new DataValidationException(new String[] {"Search term or user ID must be non null."});
        }
        return null;
    }

}
