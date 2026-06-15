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
import com.kevin.prayerappservice.request.*;
import com.kevin.prayerappservice.request.constants.PrayerRequestErrors;
import com.kevin.prayerappservice.request.constants.PrayerRequestSortField;
import com.kevin.prayerappservice.request.dtos.*;
import com.kevin.prayerappservice.request.entities.PrayerRequest;
import com.kevin.prayerappservice.request.entities.PrayerRequestBookmark;
import com.kevin.prayerappservice.request.entities.PrayerRequestComment;
import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import com.kevin.prayerappservice.request.models.*;
import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.entities.Role;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.*;

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

    @Autowired
    public PrayerRequestCommentRepository prayerRequestCommentRepository;

    @Test
    @DirtiesContext
    public void createPrayerRequest_nonMemberAttemptsToCreate_ThrowsException() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        PrayerGroup mockPrayerGroup = new PrayerGroup("Gryzzl Prayer Group", "A prayer group for Gryzzl", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequestCreateRequest createRequest = new PrayerRequestCreateRequest(1,
                mockPrayerGroup.getPrayerGroupId(), "Suffering from headaches",
                "Getting headaches, pray for healing", OffsetDateTime.now().plusMonths(1), OffsetDateTime.now());

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

        PrayerRequestCreateRequest createRequest = new PrayerRequestCreateRequest(1,
                mockPrayerGroup.getPrayerGroupId(), "Suffering from headaches",
                "Getting headaches, pray for healing", OffsetDateTime.now().plusMonths(1), OffsetDateTime.now());

        PrayerRequestCreateResult prayerRequestCreateResult = new PrayerRequestCreateResult(787,
                createRequest.getRequestTitle(), createRequest.getRequestDescription(),
                createRequest.getCreatedDate(), createRequest.getExpirationDate(), mockPrayerGroup.getPrayerGroupId()
                , mockPrayerGroup.getGroupName(), null, null, null, null, user.getUserId(), user.getUsername(),
                user.getFullName(), null, null, null, null);

        Mockito.when(prayerRequestJdbcRepository.createPrayerRequest(any())).thenReturn(prayerRequestCreateResult);

        PrayerRequestModel prayerRequestModel = prayerRequestService.createPrayerRequest("mockAuthHeader",
                createRequest);

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
                , OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        PrayerRequestGetResult prayerRequest2 = new PrayerRequestGetResult(65, "Request Title 1", "Request " +
                "Description 1", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));

        List<PrayerRequestGetResult> getResults = Arrays.asList(prayerRequest1, prayerRequest2);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequests(any())).thenReturn(getResults);

        PrayerRequestGetResponse getResponse = prayerRequestService.getPrayerRequests("mockHeader", filterCriteria);

        Assertions.assertThat(getResponse.getPrayerRequests().getFirst().getPrayerRequestId()).isEqualTo(prayerRequest1.getPrayerRequestId());
        Assertions.assertThat(getResponse.getPrayerRequests().getLast().getPrayerRequestId()).isEqualTo(prayerRequest2.getPrayerRequestId());
    }

    @Test
    public void getPrayerRequests_noUsersCommentsOrSessions_doesNotReturnIds(){
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        List<Integer> prayerGroupIds = Arrays.asList(747, 777);
        SortConfig<PrayerRequestSortField> sortConfig = new SortConfig<>(PrayerRequestSortField.CREATED_DATE,
                SortDirection.DESCENDING);

        PrayerRequestFilterCriteria filterCriteria = new PrayerRequestFilterCriteria(prayerGroupIds, 0, 20,
                sortConfig, false);

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestsCount(any())).thenReturn(new PrayerRequestCountResult(100));

        PrayerRequestGetResult prayerRequest1 = new PrayerRequestGetResult(34, "Request Title", "Request Description"
                , OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        PrayerRequestGetResult prayerRequest2 = new PrayerRequestGetResult(65, "Request Title 1", "Request " +
                "Description 1", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));

        List<PrayerRequestGetResult> getResults = Arrays.asList(prayerRequest1, prayerRequest2);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequests(any())).thenReturn(getResults);

        List<PrayerRequestUserCommentResult> userCommentResults = new ArrayList<>();
        List<PrayerRequestUserSessionResult> userSessionResults = new ArrayList<>();

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserCommentIds(any())).thenReturn(userCommentResults);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserSessionIds(any())).thenReturn(userSessionResults);

        PrayerRequestGetResponse getResponse = prayerRequestService.getPrayerRequests("mockHeader", filterCriteria);

        PrayerRequestModel prayerRequest1Model = getResponse.getPrayerRequests().getFirst();
        PrayerRequestModel prayerRequest2Model = getResponse.getPrayerRequests().getLast();

        Assertions.assertThat(prayerRequest1Model.getUserCommentIds()).isNullOrEmpty();
        Assertions.assertThat(prayerRequest2Model.getUserPrayerSessionIds()).isNullOrEmpty();
    }

    @Test
    public void getPrayerRequests_haveComments_returnCommentIds(){
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        List<Integer> prayerGroupIds = Arrays.asList(747, 777);
        SortConfig<PrayerRequestSortField> sortConfig = new SortConfig<>(PrayerRequestSortField.CREATED_DATE,
                SortDirection.DESCENDING);

        PrayerRequestFilterCriteria filterCriteria = new PrayerRequestFilterCriteria(prayerGroupIds, 0, 20,
                sortConfig, false);

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestsCount(any())).thenReturn(new PrayerRequestCountResult(100));

        PrayerRequestGetResult prayerRequest1 = new PrayerRequestGetResult(34, "Request Title", "Request Description"
                , OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        PrayerRequestGetResult prayerRequest2 = new PrayerRequestGetResult(65, "Request Title 1", "Request " +
                "Description 1", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));

        List<PrayerRequestGetResult> getResults = Arrays.asList(prayerRequest1, prayerRequest2);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequests(any())).thenReturn(getResults);

        PrayerRequestUserCommentResult userCommentResult1 = new PrayerRequestUserCommentResult(prayerRequest1.getPrayerRequestId(), 320);
        PrayerRequestUserCommentResult userCommentResult2 = new PrayerRequestUserCommentResult(prayerRequest1.getPrayerRequestId(), 330);

        PrayerRequestUserCommentResult userCommentResult3 = new PrayerRequestUserCommentResult(prayerRequest2.getPrayerRequestId(), 340);

        List<PrayerRequestUserCommentResult> userCommentResults = new ArrayList<>(List.of(userCommentResult1, userCommentResult2, userCommentResult3));
        List<PrayerRequestUserSessionResult> userSessionResults = new ArrayList<>();

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserCommentIds(any())).thenReturn(userCommentResults);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserSessionIds(any())).thenReturn(userSessionResults);

        PrayerRequestGetResponse getResponse = prayerRequestService.getPrayerRequests("mockHeader", filterCriteria);

        PrayerRequestModel prayerRequest1Model = getResponse.getPrayerRequests().getFirst();
        PrayerRequestModel prayerRequest2Model = getResponse.getPrayerRequests().getLast();

        Assertions.assertThat(prayerRequest1Model.getUserCommentIds()).contains(userCommentResult1.getPrayerRequestCommentId(), userCommentResult2.getPrayerRequestCommentId());
        Assertions.assertThat(prayerRequest2Model.getUserCommentIds()).contains(userCommentResult3.getPrayerRequestCommentId());
    }

    @Test
    public void getPrayerRequests_havePrayerSessions_returnsPrayerSessionIds(){
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId("mockToken")).thenReturn(1);

        List<Integer> prayerGroupIds = Arrays.asList(747, 777);
        SortConfig<PrayerRequestSortField> sortConfig = new SortConfig<>(PrayerRequestSortField.CREATED_DATE,
                SortDirection.DESCENDING);

        PrayerRequestFilterCriteria filterCriteria = new PrayerRequestFilterCriteria(prayerGroupIds, 0, 20,
                sortConfig, false);

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestsCount(any())).thenReturn(new PrayerRequestCountResult(100));

        PrayerRequestGetResult prayerRequest1 = new PrayerRequestGetResult(34, "Request Title", "Request Description"
                , OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        PrayerRequestGetResult prayerRequest2 = new PrayerRequestGetResult(65, "Request Title 1", "Request " +
                "Description 1", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));

        List<PrayerRequestGetResult> getResults = Arrays.asList(prayerRequest1, prayerRequest2);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequests(any())).thenReturn(getResults);

        PrayerRequestUserSessionResult userSessionResult1 = new PrayerRequestUserSessionResult(prayerRequest2.getPrayerRequestId(), 350);
        PrayerRequestUserSessionResult userSessionResult2 = new PrayerRequestUserSessionResult(prayerRequest2.getPrayerRequestId(), 380);

        PrayerRequestUserSessionResult userSessionResult3 = new PrayerRequestUserSessionResult(prayerRequest1.getPrayerRequestId(), 220);

        List<PrayerRequestUserCommentResult> userCommentResults = new ArrayList<>();
        List<PrayerRequestUserSessionResult> userSessionResults = new ArrayList<>(List.of(userSessionResult1, userSessionResult2, userSessionResult3));

        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserCommentIds(any())).thenReturn(userCommentResults);
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserSessionIds(any())).thenReturn(userSessionResults);

        PrayerRequestGetResponse getResponse = prayerRequestService.getPrayerRequests("mockHeader", filterCriteria);

        PrayerRequestModel prayerRequest1Model = getResponse.getPrayerRequests().getFirst();
        PrayerRequestModel prayerRequest2Model = getResponse.getPrayerRequests().getLast();

        Assertions.assertThat(prayerRequest1Model.getUserPrayerSessionIds()).contains(userSessionResult3.getPrayerSessionId());
        Assertions.assertThat(prayerRequest2Model.getUserPrayerSessionIds()).contains(userSessionResult1.getPrayerSessionId(), userSessionResult2.getPrayerSessionId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestLike_prayerRequestLikeExists_throwsException() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 0, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(OffsetDateTime.now(), user, prayerRequest);
        prayerRequestLikeRepository.save(prayerRequestLike);

        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(),
                OffsetDateTime.now());

        Assertions.assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> prayerRequestService.createPrayerRequestLike(prayerRequest.getPrayerRequestId(),
                        createRequest));

    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestLike_givenValidParameters_createsPrayerRequestLike() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 0, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(),
                OffsetDateTime.now());

        PrayerRequestLikeModel prayerRequestLike =
                prayerRequestService.createPrayerRequestLike(prayerRequest.getPrayerRequestId(), createRequest);

        Assertions.assertThat(prayerRequest.getLikeCount()).isEqualTo(1);
        Assertions.assertThat(prayerRequestLike.getPrayerRequestId()).isEqualTo(prayerRequest.getPrayerRequestId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequestLike_nonSubmittedUserDeletes_throwsException() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 1, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(OffsetDateTime.now(), user, prayerRequest);
        prayerRequestLikeRepository.save(prayerRequestLike);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId() + 1);

        Assertions
                .assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> prayerRequestService.deletePrayerRequestLike("mockHeader",
                        prayerRequestLike.getPrayerRequestLikeId()));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequestLike_givenValidValues_deletesPrayerRequestLike() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 1, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(OffsetDateTime.now(), user, prayerRequest);
        prayerRequestLikeRepository.save(prayerRequestLike);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId());

        int prayerRequestLikeId = prayerRequestLike.getPrayerRequestLikeId();

        prayerRequestService.deletePrayerRequestLike("mockHeader", prayerRequestLikeId);

        Optional<PrayerRequestLike> prayerRequestLikeAfterDeletion =
                prayerRequestLikeRepository.findById(prayerRequestLikeId);

        Assertions.assertThat(prayerRequestLikeAfterDeletion.isEmpty()).isTrue();
        Assertions.assertThat(prayerRequest.getLikeCount()).isEqualTo(0);
    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestBookmark_bookmarkAlreadyExists_throwsException() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 1, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestBookmark prayerRequestBookmark = new PrayerRequestBookmark(prayerRequest, user,
                OffsetDateTime.now());
        prayerRequestBookmarkRepository.save(prayerRequestBookmark);

        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(),
                OffsetDateTime.now());

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.createPrayerRequestBookmark(prayerRequest.getPrayerRequestId(), createRequest));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void createPrayerRequestBookmark_givenValidValues_createsBookmark() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 1, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);
        PrayerRequestActionCreateRequest createRequest = new PrayerRequestActionCreateRequest(user.getUserId(),
                OffsetDateTime.now());

        PrayerRequestBookmarkModel prayerRequestBookmarkModel =
                prayerRequestService.createPrayerRequestBookmark(prayerRequest.getPrayerRequestId(), createRequest);

        Assertions.assertThat(prayerRequestBookmarkModel.getPrayerRequestId()).isEqualTo(prayerRequest.getPrayerRequestId());
        Assertions.assertThat(prayerRequestBookmarkModel.getSubmittedUserId()).isEqualTo(user.getUserId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequestBookmark_nonSubmitterDeletes_throwsException() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 1, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestBookmark prayerRequestBookmark = new PrayerRequestBookmark(prayerRequest, user,
                OffsetDateTime.now());
        prayerRequestBookmarkRepository.save(prayerRequestBookmark);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId() + 1);

        Assertions
                .assertThatExceptionOfType(DataValidationException.class)
                .isThrownBy(() -> prayerRequestService.deletePrayerRequestBookmark("mockHeader",
                        prayerRequestBookmark.getPrayerRequestBookmarkId()));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequestBookmark_givenValidValues_deletesBookmark() {
        User user = new User("戴风", "demo", "demo@kandk.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup mockPrayerGroup = new PrayerGroup("K&K", "K&K俱乐部的祷告小组", null,
                VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("父母离婚", "请为我父母离婚祷告", OffsetDateTime.now(), 1, 0, 0, null,
                mockPrayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestBookmark prayerRequestBookmark = new PrayerRequestBookmark(prayerRequest, user,
                OffsetDateTime.now());
        prayerRequestBookmarkRepository.save(prayerRequestBookmark);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId());

        int bookmarkId = prayerRequestBookmark.getPrayerRequestBookmarkId();

        prayerRequestService.deletePrayerRequestBookmark("mockHeader", bookmarkId);

        Optional<PrayerRequestBookmark> bookmarkAfterDelete = prayerRequestBookmarkRepository.findById(bookmarkId);

        Assertions.assertThat(bookmarkAfterDelete.isEmpty()).isTrue();
    }

    @Test
    public void getPrayerRequest_nonJoinedUserViewsPrivateRequest_ThrowsException() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(757);

        UncategorizedSQLException userNotJoinedException = new UncategorizedSQLException(null, null,
                new SQLException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW));
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequest(anyInt(), anyInt())).thenThrow(userNotJoinedException);

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.getPrayerRequest("mockHeader", 787));
    }

    @Test
    public void getPrayerRequest_userHasPermissionToView_returnsPrayerRequest() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(757);

        PrayerRequestGetResult prayerRequestGetResult = new PrayerRequestGetResult(787, "Pray for my uncle", "Pray " +
                "for my uncle's surgery", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        prayerRequestGetResult.setCommentCount(1);

        PrayerRequestCommentResult[] commentResults = {new PrayerRequestCommentResult(777, OffsetDateTime.now(), "Will" +
                " pray for this", 717, "captainrex", "CT-7567", null, null, null, null)};

        Mockito.when(prayerRequestRepository.getPrayerRequest(anyInt(), anyInt())).thenReturn(prayerRequestGetResult);
        Mockito.when(prayerRequestRepository.getPrayerRequestComments(anyInt())).thenReturn(List.of(commentResults));

        PrayerRequestDetailsModel prayerRequestModel = prayerRequestService.getPrayerRequest("mockHeader", 787);

        Assertions.assertThat(prayerRequestModel.getPrayerRequestId()).isEqualTo(prayerRequestGetResult.getPrayerRequestId());
        Assertions.assertThat(prayerRequestModel.getRequestTitle()).isEqualTo(prayerRequestGetResult.getRequestTitle());

        Assertions.assertThat(prayerRequestModel.getComments().size()).isEqualTo(commentResults.length);
        Assertions.assertThat(prayerRequestModel.getComments().getFirst().getPrayerRequestCommentId()).isEqualTo(commentResults[0].getPrayerRequestCommentId());
    }

    @Test
    public void getPrayerRequest_hasNoUserCommentsOrSessions_doesNotReturnIds() {
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(757);

        PrayerRequestGetResult prayerRequestGetResult = new PrayerRequestGetResult(787, "Pray for my uncle", "Pray " +
                "for my uncle's surgery", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        prayerRequestGetResult.setCommentCount(1);

        PrayerRequestCommentResult[] commentResults = {new PrayerRequestCommentResult(777, OffsetDateTime.now(), "Will" +
                " pray for this", 717, "captainrex", "CT-7567", null, null, null, null)};

        Mockito.when(prayerRequestRepository.getPrayerRequest(anyInt(), anyInt())).thenReturn(prayerRequestGetResult);
        Mockito.when(prayerRequestRepository.getPrayerRequestComments(anyInt())).thenReturn(List.of(commentResults));

        List<PrayerRequestUserSessionResult> prayerRequestUserSessionResults = new ArrayList<>();
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserSessionIds(any())).thenReturn(prayerRequestUserSessionResults);

        PrayerRequestDetailsModel prayerRequestModel = prayerRequestService.getPrayerRequest("mockHeader", 787);

        Assertions.assertThat(prayerRequestModel.getUserCommentIds()).isNullOrEmpty();
        Assertions.assertThat(prayerRequestModel.getUserPrayerSessionIds()).isNullOrEmpty();
    }

    @Test
    public void getPrayerRequest_hasUserComments_returnsCommentIds() {
        int mockUserId = 757;

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(mockUserId);

        PrayerRequestGetResult prayerRequestGetResult = new PrayerRequestGetResult(787, "Pray for my uncle", "Pray " +
                "for my uncle's surgery", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        prayerRequestGetResult.setCommentCount(2);

        PrayerRequestCommentResult[] commentResults = {new PrayerRequestCommentResult(777, OffsetDateTime.now(), "Will" +
                " pray for this", 717, "captainrex", "CT-7567", null, null, null, null),
            new PrayerRequestCommentResult(787, OffsetDateTime.now(), "In my book, experience outranks everything.", mockUserId, "commanderfox", "CC-1010", null, null, null, null)};

        Mockito.when(prayerRequestRepository.getPrayerRequest(anyInt(), anyInt())).thenReturn(prayerRequestGetResult);
        Mockito.when(prayerRequestRepository.getPrayerRequestComments(anyInt())).thenReturn(List.of(commentResults));

        List<PrayerRequestUserSessionResult> prayerRequestUserSessionResults = new ArrayList<>();
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserSessionIds(any())).thenReturn(prayerRequestUserSessionResults);

        PrayerRequestDetailsModel prayerRequestModel = prayerRequestService.getPrayerRequest("mockHeader", 787);

        Assertions.assertThat(prayerRequestModel.getUserCommentIds()).contains(commentResults[1].getPrayerRequestCommentId());
    }

    @Test
    public void getPrayerRequest_hasUserPrayerSessions_returnsPrayerSessionIds() {
        int mockUserId = 757;

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(mockUserId);

        PrayerRequestGetResult prayerRequestGetResult = new PrayerRequestGetResult(787, "Pray for my uncle", "Pray " +
                "for my uncle's surgery", OffsetDateTime.now(), OffsetDateTime.now().plusDays(15));
        prayerRequestGetResult.setCommentCount(2);

        PrayerRequestCommentResult[] commentResults = {new PrayerRequestCommentResult(777, OffsetDateTime.now(), "Will" +
                " pray for this", 717, "captainrex", "CT-7567", null, null, null, null),
                new PrayerRequestCommentResult(787, OffsetDateTime.now(), "In my book, experience outranks everything.", mockUserId, "commanderfox", "CC-1010", null, null, null, null)};

        Mockito.when(prayerRequestRepository.getPrayerRequest(anyInt(), anyInt())).thenReturn(prayerRequestGetResult);
        Mockito.when(prayerRequestRepository.getPrayerRequestComments(anyInt())).thenReturn(List.of(commentResults));

        PrayerRequestUserSessionResult userSessionResult = new PrayerRequestUserSessionResult(prayerRequestGetResult.getPrayerRequestId(), 2707);

        List<PrayerRequestUserSessionResult> prayerRequestUserSessionResults = new ArrayList<>(List.of(userSessionResult));
        Mockito.when(prayerRequestJdbcRepository.getPrayerRequestUserSessionIds(any())).thenReturn(prayerRequestUserSessionResults);

        PrayerRequestDetailsModel prayerRequestModel = prayerRequestService.getPrayerRequest("mockHeader", 787);

        Assertions.assertThat(prayerRequestModel.getUserPrayerSessionIds()).contains(userSessionResult.getPrayerSessionId());
    }

    @Test
    public void createPrayerRequestComment_userIsNotMember_throwsException(){
        int mockUserId = 757;

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(mockUserId);

        PrayerRequestCommentCreateRequest createRequest = new PrayerRequestCommentCreateRequest(mockUserId, "Can I share this during our church's prayer meeting?", OffsetDateTime.now());

        UncategorizedSQLException notJoinedException = new UncategorizedSQLException(null, null, new SQLException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_COMMENT));
        Mockito.when(prayerRequestJdbcRepository.createPrayerRequestComment(any())).thenThrow(notJoinedException);

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.createPrayerRequestComment("mockHeader", 787, createRequest));
    }

    @Test
    public void createPrayerRequestComment_givenValidComment_createsComment(){
        int mockUserId = 757;

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(mockUserId);

        PrayerRequestCommentCreateRequest createRequest = new PrayerRequestCommentCreateRequest(mockUserId, "Can I share this during our church's prayer meeting?", OffsetDateTime.now());
        PrayerRequestCommentResult commentResult = new PrayerRequestCommentResult(717, OffsetDateTime.now(), createRequest.getComment(), mockUserId, "lneal", "Link Neal", null, null, null, null);

        Mockito.when(prayerRequestJdbcRepository.createPrayerRequestComment(any())).thenReturn(commentResult);

        PrayerRequestCommentModel prayerRequestCommentModel = prayerRequestService.createPrayerRequestComment("mockHeader",787, createRequest);

        Assertions.assertThat(prayerRequestCommentModel.getPrayerRequestCommentId()).isEqualTo(commentResult.getPrayerRequestCommentId());
        Assertions.assertThat(prayerRequestCommentModel.getComment()).isEqualTo(commentResult.getComment());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void updatePrayerRequest_updatedByNonSubmittedUser_throwsException(){
        User user = new User("Link Neal", "lneal", "lneal@mythical.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup prayerGroup = new PrayerGroup("Mythical Entertainment", "A prayer group for the makers of Good Mythical Morning", "", VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(prayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("Mock Prayer Request", "Mock prayer request description", OffsetDateTime.now(), 0, 0, 0, null, prayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestComment prayerRequestComment = new PrayerRequestComment("Mock prayer request comment", prayerRequest, user, OffsetDateTime.now());
        prayerRequestCommentRepository.save(prayerRequestComment);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId() + 1);

        PrayerRequestCommentUpdateRequest updateRequest = new PrayerRequestCommentUpdateRequest("Comment submitted by other user");

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.updatePrayerRequestComment("mockHeader", prayerRequestComment.getPrayerRequestCommentId(), updateRequest));

    }

    @Test
    @DirtiesContext
    @Transactional
    public void updatePrayerRequest_updatedBySubmittedUser_updatesComment(){
        User user = new User("Link Neal", "lneal", "lneal@mythical.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup prayerGroup = new PrayerGroup("Mythical Entertainment", "A prayer group for the makers of Good Mythical Morning", "", VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(prayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("Mock Prayer Request", "Mock prayer request description", OffsetDateTime.now(), 0, 0, 0, null, prayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestComment prayerRequestComment = new PrayerRequestComment("Mock prayer request comment", prayerRequest, user, OffsetDateTime.now());
        prayerRequestCommentRepository.save(prayerRequestComment);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId());

        PrayerRequestCommentUpdateRequest updateRequest = new PrayerRequestCommentUpdateRequest("Comment submitted by same user");
        PrayerRequestCommentModel prayerRequestCommentModel = prayerRequestService.updatePrayerRequestComment("mockHeader", prayerRequestComment.getPrayerRequestCommentId(), updateRequest);

        Assertions.assertThat(prayerRequestCommentModel.getComment()).isEqualTo(updateRequest.getComment());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequest_nonSubmittedUserAttempts_throwsException(){
        User user = new User("Link Neal", "lneal", "lneal@mythical.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup prayerGroup = new PrayerGroup("Mythical Entertainment", "A prayer group for the makers of Good Mythical Morning", "", VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(prayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("Mock Prayer Request", "Mock prayer request description", OffsetDateTime.now(), 0, 0, 0, null, prayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestComment prayerRequestComment = new PrayerRequestComment("Mock prayer request comment", prayerRequest, user, OffsetDateTime.now());
        prayerRequestCommentRepository.save(prayerRequestComment);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId() + 1);

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.deletePrayerRequestComment("mockHeader", prayerRequestComment.getPrayerRequestCommentId()));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deletePrayerRequest_submittedUserDeletes_deletesPrayerRequest(){
        User user = new User("Link Neal", "lneal", "lneal@mythical.com", "mockPasswordHash", Role.USER);
        userRepository.save(user);

        PrayerGroup prayerGroup = new PrayerGroup("Mythical Entertainment", "A prayer group for the makers of Good Mythical Morning", "", VisibilityLevel.PRIVATE, null, null);
        prayerGroupRepository.save(prayerGroup);

        PrayerRequest prayerRequest = new PrayerRequest("Mock Prayer Request", "Mock prayer request description", OffsetDateTime.now(), 0, 0, 0, null, prayerGroup, user);
        prayerRequestRepository.save(prayerRequest);

        PrayerRequestComment prayerRequestComment = new PrayerRequestComment("Mock prayer request comment", prayerRequest, user, OffsetDateTime.now());
        prayerRequestCommentRepository.save(prayerRequestComment);

        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(user.getUserId());

        int prayerRequestCommentId = prayerRequestComment.getPrayerRequestCommentId();

        prayerRequestService.deletePrayerRequestComment("mockHeader", prayerRequestCommentId);

        ArgumentCaptor<Integer> prayerRequestCommentIdCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(prayerRequestJdbcRepository).deletePrayerRequestComment(prayerRequestCommentIdCaptor.capture());

        Integer calledId = prayerRequestCommentIdCaptor.getValue();
        Assertions.assertThat(calledId).isEqualTo(prayerRequestCommentId);
    }

    @Test
    public void updatePrayerRequest_whenNotCalledBySubmittedUser_throwsError(){
        int mockUserId = 737;
        Mockito.when(jwtService.extractTokenFromAuthHeader(anyString())).thenReturn("mockToken");
        Mockito.when(jwtService.extractUserId(anyString())).thenReturn(mockUserId);

        UncategorizedSQLException userDoesNotMatchException = new UncategorizedSQLException(null, null, new SQLException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_UPDATE_REQUEST));
        Mockito.when(prayerRequestJdbcRepository.updatePrayerRequest(any())).thenThrow(userDoesNotMatchException);

        OffsetDateTime expirationDate = OffsetDateTime.now();
        expirationDate = expirationDate.plusDays(15);

        PrayerRequestUpdateRequest updateRequest = new PrayerRequestUpdateRequest("Prayer request 1", "Prayer request description 1", expirationDate);

        Assertions.assertThatExceptionOfType(DataValidationException.class).isThrownBy(() -> prayerRequestService.updatePrayerRequest("mockHeader", 747, updateRequest));
    }
}
