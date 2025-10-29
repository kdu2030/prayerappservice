package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.request.PrayerRequestService;
import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
public class PrayerRequestServiceTests {
    @MockBean
    private JwtService jwtService;

    @Autowired
    private PrayerGroupRepository prayerGroupRepository;

    @Autowired
    private PrayerGroupUserRepository prayerGroupUserRepository;

    @Autowired
    private PrayerRequestService prayerRequestService;

    @Test
    @DirtiesContext
    public void createPrayerRequest_nonMemberAttemptsToCreate_ThrowsException(){
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        PrayerGroup mockPrayerGroup = new PrayerGroup("Gryzzl Prayer Group", "A prayer group for Gryzzl", null, VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequestCreateRequest createRequest = new PrayerRequestCreateRequest(1, "Suffering from headaches", "Getting headaches, pray for healing", LocalDateTime.now().plusMonths(1), LocalDateTime.now());

        assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.createPrayerRequest("mockAuthHeader", mockPrayerGroup.getPrayerGroupId(), createRequest));
    }


}
