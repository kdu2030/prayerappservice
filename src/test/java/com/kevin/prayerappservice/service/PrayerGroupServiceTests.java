package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.group.PrayerGroupJdbcRepositoryImpl;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.mappers.PrayerGroupMapper;
import com.kevin.prayerappservice.group.models.GroupNameValidationResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PrayerGroupServiceTests {
    @MockBean
    private PrayerGroupJdbcRepositoryImpl mockPrayerGroupJdbcRepository;

    @Autowired
    private PrayerGroupRepository prayerGroupRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PrayerGroupMapper prayerGroupMapper;

    @Autowired
    private PrayerGroupService prayerGroupService;

    @Test
    @DirtiesContext
    public void validateGroupName_returnsError_whenNameIsDuplicate() {
        PrayerGroup mockPrayerGroup = new PrayerGroup("Autobots", "Robots in disguise", null, VisibilityLevel.PUBLIC,
                null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        GroupNameValidationResponse response = prayerGroupService.validateGroupName("Autobots");

        Assertions.assertThat(response.getErrors()[0]).isEqualTo("A prayer group with this name already exists.");
    }
}
