package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.common.SortDirection;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import com.kevin.prayerappservice.request.*;
import com.kevin.prayerappservice.request.constants.PrayerRequestErrors;
import com.kevin.prayerappservice.request.constants.PrayerRequestSortField;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCountResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetResult;
import com.kevin.prayerappservice.request.entities.PrayerRequest;
import com.kevin.prayerappservice.request.entities.PrayerRequestBookmark;
import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import com.kevin.prayerappservice.request.models.*;
import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
public class PrayerRequestServiceTests {
    @MockBean
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrayerGroupRepository prayerGroupRepository;

    @Autowired
    private PrayerGroupUserRepository prayerGroupUserRepository;

    @MockBean
    private PrayerRequestJdbcRepositoryImpl prayerRequestJdbcRepository;

    @Autowired
    private PrayerRequestRepository prayerRequestRepository;

    @Autowired
    private PrayerRequestLikeRepository prayerRequestLikeRepository;

    @Autowired
    private PrayerRequestBookmarkRepository prayerRequestBookmarkRepository;

    @Autowired
    private PrayerRequestService prayerRequestService;

    @Test
    @DirtiesContext
    public void createPrayerRequest_nonMemberAttemptsToCreate_ThrowsException() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        PrayerGroup mockPrayerGroup = new PrayerGroup("Gryzzl Prayer Group", "A prayer group for Gryzzl", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequestCreateRequest createRequest = new PrayerRequestCreateRequest(1, mockPrayerGroup.getPrayerGroupId(), "Suffering from headaches",
                "Getting headaches, pray for healing", LocalDateTime.now().plusMonths(1), LocalDateTime.now());

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.createPrayerRequest("mockAuthHeader", createRequest));
    }

    @Test
    @DirtiesContext
    public void createPrayerRequest_givenValidRequest_createsPrayerRequest() {
        PrayerGroup mockPrayerGroup = new PrayerGroup("Gryzzl Prayer Group", "A prayer group for Gryzzl", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        User user = new User("Mike Bean", "mbean", "mbean@gryzzl.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(user.getUserId());

        PrayerGroupUser prayerGroupUser = new PrayerGroupUser(user, mockPrayerGroup, PrayerGroupRole.ADMIN);
        prayerGroupUserRepository.save(prayerGroupUser);

        PrayerRequestCreateRequest createRequest = new PrayerRequestCreateRequest(1, mockPrayerGroup.getPrayerGroupId(), "Suffering from headaches",
                "Getting headaches, pray for healing", LocalDateTime.now().plusMonths(1), LocalDateTime.now());

        PrayerRequestCreateResult prayerRequestCreateResult = new PrayerRequestCreateResult(787,
                createRequest.getRequestTitle(), createRequest.getRequestDescription(),
                createRequest.getCreatedDate(), createRequest.getExpirationDate(), mockPrayerGroup.getPrayerGroupId()
                , mockPrayerGroup.getGroupName(), null, null, null, null, user.getUserId(), user.getUsername(),
                user.getFullName(), null, null, null, null);

        Mockito.when(prayerRequestJdbcRepository.createPrayerRequest(any())).thenReturn(prayerRequestCreateResult);

        PrayerRequestModel prayerRequestModel = prayerRequestService.createPrayerRequest("mockAuthHeader", createRequest);

        Assertions.assertThat(prayerRequestModel.getPrayerRequestId()).isEqualTo(prayerRequestCreateResult.getPrayerRequestId());
        Assertions.assertThat(prayerRequestModel.getRequestTitle()).isEqualTo(prayerRequestCreateResult.getRequestTitle());
    }

    @Test
    public void getPrayerRequests_nonMemberQueriesPrayerRequests_throwsException() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        List<Integer> prayerGroupIds = Arrays.asList(747, 777);
        SortConfig<PrayerRequestSortField> sortConfig = new SortConfig<>(PrayerRequestSortField.CREATED_DATE,
                SortDirection.DESCENDING);

        PrayerRequestFilterCriteria filterCriteria = new PrayerRequestFilterCriteria(prayerGroupIds, 0, 20,
                sortConfig, false);

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestsCount(any())).thenReturn(new PrayerRequestCountResult(100));
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequests(any())).thenThrow(new UncategorizedSQLException(null, null, new SQLException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW)));

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.getPrayerRequests("mockAuthHeader", filterCriteria));
    }

    @Test
    public void getPrayerRequests_givenValidRequests_returnsValidRequests() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        List<Integer> prayerGroupIds = Arrays.asList(747, 777);
        SortConfig<PrayerRequestSortField> sortConfig = new SortConfig<>(PrayerRequestSortField.CREATED_DATE,
                SortDirection.DESCENDING);

        PrayerRequestFilterCriteria filterCriteria = new PrayerRequestFilterCriteria(prayerGroupIds, 0, 20,
                sortConfig, false);

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestsCount(any())).thenReturn(new PrayerRequestCountResult(100));

        PrayerRequestGetResult prayerRequest1 = new PrayerRequestGetResult(34, "Request Title", "Request Description"
                , LocalDateTime.now(), LocalDateTime.now().plusDays(15));
        PrayerRequestGetResult prayerRequest2 = new PrayerRequestGetResult(65, "Request Title 1", "Request " +
                "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(15));

        List<PrayerRequestGetResult> getResults = Arrays.asList(prayerRequest1, prayerRequest2);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequests(any())).thenReturn(getResults);

        PrayerRequestGetResponse getResponse = prayerRequestService.getPrayerRequests("mockHeader", filterCriteria);

        Assertions.assertThat(getResponse.getPrayerRequests().getFirst().getPrayerRequestId()).isEqualTo(prayerRequest1.getPrayerRequestId());
        Assertions.assertThat(getResponse.getPrayerRequests().getLast().getPrayerRequestId()).isEqualTo(prayerRequest2.getPrayerRequestId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestLike_prayerRequestLikeExists_throwsException(){
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", LocalDateTime.now(), 0, 0, 0, null, mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(LocalDateTime.now(), user, prayerRequest);
        prayerRequestLikeRepository.save(prayerRequestLike);

        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(), LocalDateTime.now());

        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> prayerRequestService.createPrayerRequestLike(prayerRequest.getPrayerRequestId(), createRequest));

    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestLike_givenValidParameters_createsPrayerRequestLike(){
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", LocalDateTime.now(), 0, 0, 0, null, mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(), LocalDateTime.now());

        PrayerRequestLikeModel prayerRequestLike = prayerRequestService.createPrayerRequestLike(prayerRequest.getPrayerRequestId(), createRequest);

        Assertions.assertThat(prayerRequest.getLikeCount()).isEqualTo(1);
        Assertions.assertThat(prayerRequestLike.getPrayerRequestId()).isEqualTo(prayerRequest.getPrayerRequestId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequestLike_nonSubmittedUserDeletes_throwsException(){
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", LocalDateTime.now(), 1, 0, 0, null, mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(LocalDateTime.now(), user, prayerRequest);
        prayerRequestLikeRepository.save(prayerRequestLike);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId() + 1);

        Assertions
                .assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> prayerRequestService.deletePrayerRequestLike("mockHeader", prayerRequestLike.getPrayerRequestLikeId()));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequestLike_givenValidValues_deletesPrayerRequestLike(){
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", LocalDateTime.now(), 1, 0, 0, null, mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(LocalDateTime.now(), user, prayerRequest);
        prayerRequestLikeRepository.save(prayerRequestLike);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId());

        int prayerRequestLikeId = prayerRequestLike.getPrayerRequestLikeId();

        prayerRequestService.deletePrayerRequestLike("mockHeader", prayerRequestLikeId);

        Optional<PrayerRequestLike> prayerRequestLikeAfterDeletion = prayerRequestLikeRepository.findById(prayerRequestLikeId);

        Assertions.assertThat(prayerRequestLikeAfterDeletion.isEmpty()).isTrue();
        Assertions.assertThat(prayerRequest.getLikeCount()).isEqualTo(0);
    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestBookmark_bookmarkAlreadyExists_throwsException(){
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", LocalDateTime.now(), 1, 0, 0, null, mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestBookmark prayerRequestBookmark = new PrayerRequestBookmark(prayerRequest, user, LocalDateTime.now());
        prayerRequestBookmarkRepository.save(prayerRequestBookmark);

        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(), LocalDateTime.now());

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.createPrayerRequestBookmark(prayerRequest.getPrayerRequestId(), createRequest));
    }
}
