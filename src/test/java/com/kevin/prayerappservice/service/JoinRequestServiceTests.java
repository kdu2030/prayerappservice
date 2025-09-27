package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.join.JoinRequestRepository;
import com.kevin.prayerappservice.join.JoinRequestService;
import com.kevin.prayerappservice.join.entities.JoinRequest;
import com.kevin.prayerappservice.join.models.JoinRequestCreateRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class JoinRequestServiceTests {
    @Autowired
    private PrayerGroupService prayerGroupService;

    @Autowired
    private PrayerGroupRepository prayerGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrayerGroupUserRepository prayerGroupUserRepository;

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private JoinRequestService joinRequestService;

    @Test
    public void createJoinRequest_unableToFindPrayerGroup_throwsException(){
        JoinRequestCreateRequest joinRequestCreateRequest = new JoinRequestCreateRequest(99, LocalDateTime.now());
        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> joinRequestService.createJoinRequest(122, joinRequestCreateRequest));
    }

    @Test
    @DirtiesContext
    public void createJoinRequest_userAlreadyJoined_throwsException(){
        User user = new User("Ben Wyatt", "bwyatt", "bwyatt@indiana.gov", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup prayerGroup = new PrayerGroup("Indiana State Government", "State government prayer group", "Rules", VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(prayerGroup);

        PrayerGroupUser mockPrayerGroupUser = new PrayerGroupUser(user, prayerGroup, PrayerGroupRole.MEMBER);
        prayerGroupUserRepository.save(mockPrayerGroupUser);

        JoinRequestCreateRequest createRequest = new JoinRequestCreateRequest(user.getUserId(), LocalDateTime.now());
        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> joinRequestService.createJoinRequest(prayerGroup.getPrayerGroupId(), createRequest));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void createJoinRequest_givenValidValues_savesJoinRequest(){
        User user = new User("Ben Wyatt", "bwyatt", "bwyatt@indiana.gov", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup prayerGroup = new PrayerGroup("Indiana State Government", "State government prayer group", "Rules", VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(prayerGroup);

        JoinRequestCreateRequest createRequest = new JoinRequestCreateRequest(user.getUserId(), LocalDateTime.now());
        JoinRequestModel joinRequestModel = joinRequestService.createJoinRequest(prayerGroup.getPrayerGroupId(), createRequest);

        Assertions.assertThat(joinRequestModel.getUser().getUserId()).isEqualTo(user.getUserId());
        Assertions.assertThat(joinRequestModel.getPrayerGroupId()).isEqualTo(prayerGroup.getPrayerGroupId());
    }

}
