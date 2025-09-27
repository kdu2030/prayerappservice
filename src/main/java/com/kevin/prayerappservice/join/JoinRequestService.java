package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.common.SortDirection;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.PrayerGroupErrorMessages;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.join.constants.JoinRequestErrorMessages;
import com.kevin.prayerappservice.join.constants.JoinRequestSortField;
import com.kevin.prayerappservice.join.dtos.JoinRequestDTO;
import com.kevin.prayerappservice.join.entities.JoinRequest;
import com.kevin.prayerappservice.join.mappers.JoinRequestMapper;
import com.kevin.prayerappservice.join.models.*;
import com.kevin.prayerappservice.user.entities.User;
import com.kevin.prayerappservice.user.models.UserSummary;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final PrayerGroupRepository prayerGroupRepository;
    private final PrayerGroupUserRepository prayerGroupUserRepository;
    private final EntityManager entityManager;
    private final JoinRequestMapper joinRequestMapper;
    private final JwtService jwtService;
    private final PrayerGroupService prayerGroupService;

    public JoinRequestService(JoinRequestRepository joinRequestRepository, PrayerGroupRepository prayerGroupRepository, EntityManager entityManager, JoinRequestMapper joinRequestMapper, PrayerGroupUserRepository prayerGroupUserRepository, JwtService jwtService, PrayerGroupService prayerGroupService) {
        this.joinRequestRepository = joinRequestRepository;
        this.prayerGroupRepository = prayerGroupRepository;
        this.entityManager = entityManager;
        this.joinRequestMapper = joinRequestMapper;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
        this.jwtService = jwtService;
        this.prayerGroupService = prayerGroupService;
    }

    public JoinRequestModel createJoinRequest(int prayerGroupId, JoinRequestCreateRequest joinRequestCreateRequest) {
        Optional<PrayerGroup> prayerGroupResponse = prayerGroupRepository.findById(prayerGroupId);
        if (prayerGroupResponse.isEmpty()) {
            throw new DataValidationException(String.format(PrayerGroupErrorMessages.CANNOT_FIND_PRAYER_GROUP, prayerGroupId));
        }

        Optional<PrayerGroupUser> prayerGroupUserResponse = prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId, joinRequestCreateRequest.getUserId());
        if (prayerGroupUserResponse.isPresent()) {
            throw new DataValidationException(JoinRequestErrorMessages.USER_ALREADY_JOINED);
        }

        User user = entityManager.getReference(User.class, joinRequestCreateRequest.getUserId());
        JoinRequest joinRequest = new JoinRequest(user, prayerGroupResponse.get(), joinRequestCreateRequest.getSubmittedDate());
        joinRequestRepository.save(joinRequest);
        return joinRequestMapper.joinRequestToJoinRequestModel(joinRequest);
    }

    public JoinRequestsGetResponse getJoinRequests(int prayerGroupId, JoinRequestsGetRequest getRequest) {
        List<JoinRequestDTO> joinRequestDTOS = joinRequestRepository.getJoinRequests(prayerGroupId);
        List<JoinRequestModel> joinRequests = joinRequestDTOS.stream()
                .map(joinRequestMapper::joinRequestDTOToJoinRequestModel)
                .sorted((joinRequestA, joinRequestB) -> compareJoinRequests(joinRequestA, joinRequestB, getRequest.getSortConfig()))
                .toList();

        return new JoinRequestsGetResponse(joinRequests);
    }

    public void deleteJoinRequests (String authToken, int prayerGroupId, JoinRequestDeleteRequest deleteRequest){
        String token = jwtService.extractTokenFromAuthHeader(authToken);
        int userId = jwtService.extractUserId(token);

        PrayerGroupRole submitterRole =  prayerGroupService.getPrayerGroupRoleForUser(prayerGroupId, userId);
        if(submitterRole != PrayerGroupRole.ADMIN){
            throw new DataValidationException(JoinRequestErrorMessages.NON_ADMIN_CANNOT_DELETE_JOIN_REQUEST);
        }

        joinRequestRepository.deleteJoinRequests(prayerGroupId, deleteRequest.getJoinRequestIds());
    }

    public void approveJoinRequests(String authToken, int prayerGroupId, JoinRequestApproveRequest approveRequest){
        String token = jwtService.extractTokenFromAuthHeader(authToken);
        int userId = jwtService.extractUserId(token);

        PrayerGroupRole submitterRole =  prayerGroupService.getPrayerGroupRoleForUser(prayerGroupId, userId);
        if(submitterRole != PrayerGroupRole.ADMIN){
            throw new DataValidationException(JoinRequestErrorMessages.NON_ADMIN_CANNOT_DELETE_JOIN_REQUEST);
        }

        joinRequestRepository.approveJoinRequests(prayerGroupId, approveRequest.getJoinRequestIds());
    }


    private int compareJoinRequests(JoinRequestModel joinRequestA, JoinRequestModel joinRequestB, SortConfig<JoinRequestSortField> sortConfig) {
        int sortCoefficient = sortConfig.getSortDirection() == SortDirection.ASCENDING ? 1 : -1;
        UserSummary userA = joinRequestA.getUser();
        UserSummary userB = joinRequestB.getUser();

        return switch (sortConfig.getSortField()) {
            case JoinRequestSortField.USERNAME -> userA.getUsername().compareTo(userB.getUsername()) * sortCoefficient;
            case JoinRequestSortField.FULL_NAME -> userA.getFullName().compareTo(userB.getFullName()) * sortCoefficient;
            case JoinRequestSortField.SUBMITTED_DATE ->
                    joinRequestA.getSubmittedDate().compareTo(joinRequestB.getSubmittedDate()) * sortCoefficient;
        };
    }

}
