package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import org.springframework.beans.factory.annotation.Autowired;

public class PrayerGroupService {
    private PrayerGroupRepository prayerGroupRepository;

    @Autowired
    public PrayerGroupService(PrayerGroupRepository prayerGroupRepository){
        this.prayerGroupRepository = prayerGroupRepository;
    }

    public void createPrayerGroup(NewPrayerGroup prayerGroup){


    }


}
