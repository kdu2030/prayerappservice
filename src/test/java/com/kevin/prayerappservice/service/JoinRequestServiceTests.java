package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.common.SortDirection;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.join.JoinRequestJdbcRepositoryImpl;
import com.kevin.prayerappservice.join.JoinRequestRepository;
import com.kevin.prayerappservice.join.JoinRequestService;
import com.kevin.prayerappservice.join.constants.JoinRequestSortField;
import com.kevin.prayerappservice.join.dtos.JoinRequestDTO;
import com.kevin.prayerappservice.join.models.JoinRequestCreateRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import com.kevin.prayerappservice.join.models.JoinRequestsGetRequest;
import com.kevin.prayerappservice.join.models.JoinRequestsGetResponse;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

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

    @MockBean
    private JoinRequestJdbcRepositoryImpl joinRequestJdbcRepository;

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

    @Test
    public void getJoinRequests_sortByDateConfig_isSorted(){
        JoinRequestDTO joinRequestDTO1 = new JoinRequestDTO(320, 350, LocalDateTime.parse("2023-05-04T03:00:00"), 380, "Tom Haverford", "thaverford", 56, "tom_haverford.png", "https://fileservices.pythonanywhere.com/static/tom_haverford.png", FileType.IMAGE.toString());
        JoinRequestDTO joinRequestDTO2 = new JoinRequestDTO(342, 122, LocalDateTime.parse("2022-03-14T09:00:00"), 423, "Ann Perkins", "aperkins", null, null, null, null);
        JoinRequestDTO joinRequestDTO3 = new JoinRequestDTO(623, 123, LocalDateTime.parse("2025-02-02T07:00:00"), 912, "Ron Swanson", "rswanson", 442, "rswanson_profile.png", "https://prayerappfileservices.pythonanywhere.com/rswanson_profile.png", FileType.IMAGE.toString());

        List<JoinRequestDTO> mockJoinRequests = List.of(joinRequestDTO1, joinRequestDTO2, joinRequestDTO3);

        Mockito.when(joinRequestJdbcRepository.getJoinRequests(anyInt())).thenReturn(mockJoinRequests);

        SortConfig<JoinRequestSortField> sortConfig = new SortConfig<>(JoinRequestSortField.SUBMITTED_DATE, SortDirection.DESCENDING);
        JoinRequestsGetRequest getRequest = new JoinRequestsGetRequest(sortConfig);

        JoinRequestsGetResponse response = joinRequestService.getJoinRequests(42, getRequest);

        List<Integer> receivedJoinRequestIds = response.getJoinRequests().stream().map(JoinRequestModel::getJoinRequestId).toList();
        List<Integer> expectedJoinRequestIds = List.of(joinRequestDTO3.getJoinRequestId(), joinRequestDTO1.getJoinRequestId(), joinRequestDTO2.getJoinRequestId());

        Assertions.assertThat(receivedJoinRequestIds).isEqualTo(expectedJoinRequestIds);
    }

    @Test
    public void getJoinRequests_sortByUsernameConfig_isSorted(){
        JoinRequestDTO joinRequestDTO1 = new JoinRequestDTO(320, 350, LocalDateTime.parse("2023-05-04T03:00:00"), 380, "mockFullName", "thaverford", 56, "tom_haverford.png", "https://fileservices.pythonanywhere.com/static/tom_haverford.png", FileType.IMAGE.toString());
        JoinRequestDTO joinRequestDTO2 = new JoinRequestDTO(342, 122, LocalDateTime.parse("2023-05-04T03:00:00"), 423, "mockFullName", "aperkins", null, null, null, null);
        JoinRequestDTO joinRequestDTO3 = new JoinRequestDTO(623, 123, LocalDateTime.parse("2023-05-04T03:00:00"), 912, "mockFullName", "rswanson", 442, "rswanson_profile.png", "https://prayerappfileservices.pythonanywhere.com/rswanson_profile.png", FileType.IMAGE.toString());

        List<JoinRequestDTO> mockJoinRequests = List.of(joinRequestDTO1, joinRequestDTO2, joinRequestDTO3);
        Mockito.when(joinRequestJdbcRepository.getJoinRequests(anyInt())).thenReturn(mockJoinRequests);

        SortConfig<JoinRequestSortField> sortConfig = new SortConfig<>(JoinRequestSortField.USERNAME, SortDirection.ASCENDING);
        JoinRequestsGetRequest getRequest = new JoinRequestsGetRequest(sortConfig);

        JoinRequestsGetResponse response = joinRequestService.getJoinRequests(42, getRequest);

        List<Integer> receivedJoinRequestIds = response.getJoinRequests().stream().map(JoinRequestModel::getJoinRequestId).toList();
        List<Integer> expectedJoinRequestIds = List.of(joinRequestDTO2.getJoinRequestId(), joinRequestDTO3.getJoinRequestId(), joinRequestDTO1.getJoinRequestId());

        Assertions.assertThat(receivedJoinRequestIds).isEqualTo(expectedJoinRequestIds);
    }

    @Test
    public void getJoinRequests_sortByFullNameConfig_isSorted(){
        JoinRequestDTO joinRequestDTO1 = new JoinRequestDTO(320, 350, LocalDateTime.parse("2023-05-04T03:00:00"), 380, "Tom Haverford", "mockUsername", 56, "tom_haverford.png", "https://fileservices.pythonanywhere.com/static/tom_haverford.png", FileType.IMAGE.toString());
        JoinRequestDTO joinRequestDTO2 = new JoinRequestDTO(342, 122, LocalDateTime.parse("2023-05-04T03:00:00"), 423, "Ann Perkins", "mockUsername", null, null, null, null);
        JoinRequestDTO joinRequestDTO3 = new JoinRequestDTO(623, 123, LocalDateTime.parse("2023-05-04T03:00:00"), 912, "Ron Swanson", "mockUsername", 442, "rswanson_profile.png", "https://prayerappfileservices.pythonanywhere.com/rswanson_profile.png", FileType.IMAGE.toString());

        List<JoinRequestDTO> mockJoinRequests = List.of(joinRequestDTO1, joinRequestDTO2, joinRequestDTO3);
        Mockito.when(joinRequestJdbcRepository.getJoinRequests(anyInt())).thenReturn(mockJoinRequests);

        SortConfig<JoinRequestSortField> sortConfig = new SortConfig<>(JoinRequestSortField.FULL_NAME, SortDirection.ASCENDING);
        JoinRequestsGetRequest getRequest = new JoinRequestsGetRequest(sortConfig);

        JoinRequestsGetResponse response = joinRequestService.getJoinRequests(42, getRequest);

        List<Integer> receivedJoinRequestIds = response.getJoinRequests().stream().map(JoinRequestModel::getJoinRequestId).toList();
        List<Integer> expectedJoinRequestIds = List.of(joinRequestDTO2.getJoinRequestId(), joinRequestDTO3.getJoinRequestId(), joinRequestDTO1.getJoinRequestId());

        Assertions.assertThat(receivedJoinRequestIds).isEqualTo(expectedJoinRequestIds);
    }

}
