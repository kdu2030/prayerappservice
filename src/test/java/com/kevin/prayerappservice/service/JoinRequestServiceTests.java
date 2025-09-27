package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.join.JoinRequestService;
import com.kevin.prayerappservice.join.models.JoinRequestCreateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class JoinRequestServiceTests {
    @Autowired
    private PrayerGroupService prayerGroupService;

    @Autowired
    private PrayerGroupRepository prayerGroupRepository;

    @Autowired
    private JoinRequestService joinRequestService;

    @Test
    public void createJoinRequest_unableToFindPrayerGroup_throwsException(){
        JoinRequestCreateRequest joinRequestCreateRequest = new JoinRequestCreateRequest(99, LocalDateTime.now());
        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> joinRequestService.createJoinRequest(122, joinRequestCreateRequest));
    }

}
