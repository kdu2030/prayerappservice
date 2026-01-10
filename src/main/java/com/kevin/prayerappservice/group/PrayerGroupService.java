package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.common.SortConfig;
import com.kevin.prayerappservice.common.SortDirection;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.PrayerGroupErrorMessages;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.PrayerGroupUserSortField;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.dtos.*;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.group.mappers.PrayerGroupMapper;
import com.kevin.prayerappservice.group.models.*;
import com.kevin.prayerappservice.join.JoinRequestRepository;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PrayerGroupService {
    private final PrayerGroupRepository prayerGroupRepository;
    private final JwtService jwtService;
    private final PrayerGroupMapper prayerGroupMapper;
    private final PrayerGroupUserRepository prayerGroupUserRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final EntityManager entityManager;

    public PrayerGroupService(PrayerGroupRepository prayerGroupRepository, JwtService jwtService,
                              PrayerGroupMapper prayerGroupMapper,
                              PrayerGroupUserRepository prayerGroupUserRepository,
                              JoinRequestRepository joinRequestRepository, EntityManager entityManager) {
        this.prayerGroupRepository = prayerGroupRepository;
        this.jwtService = jwtService;
        this.prayerGroupMapper = prayerGroupMapper;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.entityManager = entityManager;
    }

    public PrayerGroupModel createPrayerGroup(String authorizationHeader, CreatePrayerGroupRequest prayerGroupRequest) {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);
        VisibilityLevel visibilityLevel =
                Optional.ofNullable(prayerGroupRequest.getVisibilityLevel()).orElse(VisibilityLevel.PUBLIC);

        CreatePrayerGroupRequestDTO createPrayerGroupRequestDTO = new CreatePrayerGroupRequestDTO(userId,
                prayerGroupRequest.getGroupName(), prayerGroupRequest.getDescription(), prayerGroupRequest.getRules(),
                visibilityLevel.toString(), prayerGroupRequest.getAvatarFileId(), prayerGroupRequest.getBannerFileId());

        CreatedPrayerGroupDTO createdPrayerGroupDTO =
                prayerGroupRepository.createPrayerGroup(createPrayerGroupRequestDTO);
        return prayerGroupMapper.createdPrayerGroupDTOToPrayerGroupModel(createdPrayerGroupDTO);
    }

    public GroupNameValidationResponse validateGroupName(String groupName) {
        boolean isNameValid = true;
        String[] errors = null;
        Optional<PrayerGroup> prayerGroup = prayerGroupRepository.findByGroupName(groupName);

        if (prayerGroup.isPresent()) {
            isNameValid = false;
            errors = new String[]{"A prayer group with this name already exists."};
        }

        return new GroupNameValidationResponse(isNameValid, errors);
    }

    public List<PrayerGroupSummaryModel> getPrayerGroupSummariesByUser(int userId) {
        List<PrayerGroupSummaryDTO> prayerGroupSummaries =
                prayerGroupRepository.getPrayerGroupSummariesByUserId(userId);
        return prayerGroupSummaries.stream()
                .map(prayerGroupMapper::prayerGroupSummaryDTOToPrayerGroupSummaryModel)
                .toList();
    }

    public PrayerGroupModel getPrayerGroup(String authorizationHeader, int prayerGroupId) {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);

        PrayerGroupDTO prayerGroupDTO = prayerGroupRepository.getPrayerGroup(prayerGroupId, userId);
        List<PrayerGroupUserDTO> adminUserDTOs = prayerGroupRepository.getPrayerGroupUsers(prayerGroupId,
                List.of(new PrayerGroupRole[]{PrayerGroupRole.ADMIN}));

        PrayerGroupModel prayerGroup = prayerGroupMapper.prayerGroupDTOToPrayerGroupModel(prayerGroupDTO);
        List<PrayerGroupUserModel> adminUsers =
                adminUserDTOs.stream().map(prayerGroupMapper::prayerGroupUserDTOToPrayerGroupUserModel).toList();

        prayerGroup.setAdmins(adminUsers);
        return prayerGroup;
    }

    public PrayerGroupModel updatePrayerGroup(String authorizationHeader, int prayerGroupId,
                                              PutPrayerGroupRequest putPrayerGroupRequest) {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);

        PrayerGroupUser user = prayerGroupUserRepository
                .findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId, userId)
                .orElseThrow(() -> new DataValidationException("User must be part of prayer group to modify it."));

        if (user.getPrayerGroupRole() != PrayerGroupRole.ADMIN) {
            throw new DataValidationException("User must be an admin to modify the prayer group");
        }

        VisibilityLevel visibilityLevel =
                Optional.ofNullable(putPrayerGroupRequest.getVisibilityLevel()).orElse(VisibilityLevel.PUBLIC);

        if (visibilityLevel == VisibilityLevel.PUBLIC && hasActiveJoinRequests(prayerGroupId)) {
            throw new DataValidationException("Cannot set visibility level to PUBLIC with active join requests.");
        }

        Integer avatarFileId = putPrayerGroupRequest.getAvatarFileId();
        MediaFile avatarFile = avatarFileId != null ? entityManager.getReference(MediaFile.class, avatarFileId) : null;

        Integer bannerFileId = putPrayerGroupRequest.getBannerFileId();
        MediaFile bannerFile = bannerFileId != null ? entityManager.getReference(MediaFile.class, bannerFileId) : null;

        PrayerGroup updatedPrayerGroup = new PrayerGroup(prayerGroupId, putPrayerGroupRequest.getGroupName(),
                putPrayerGroupRequest.getDescription(), putPrayerGroupRequest.getRules(), visibilityLevel, avatarFile
                , bannerFile);

        prayerGroupRepository.save(updatedPrayerGroup);

        return getPrayerGroup(authorizationHeader, prayerGroupId);
    }

    public PrayerGroupUserModel addPrayerGroupUser(String authorizationHeader, int prayerGroupId, int userId) {
        PrayerGroup prayerGroup = prayerGroupRepository.findById(prayerGroupId)
                .orElseThrow(() -> new DataValidationException(String.format(PrayerGroupErrorMessages.CANNOT_FIND_PRAYER_GROUP, prayerGroupId)));

        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int submitterUserId = jwtService.extractUserId(token);

        PrayerGroupRole submitterRole = getPrayerGroupRoleForUser(prayerGroupId, submitterUserId);

        if (submitterUserId != userId && submitterRole != PrayerGroupRole.ADMIN) {
            throw new DataValidationException(PrayerGroupErrorMessages.MUST_BE_ADMIN_TO_ADD);
        }

        if (prayerGroup.getVisibilityLevel() == VisibilityLevel.PRIVATE && submitterRole != PrayerGroupRole.ADMIN) {
            throw new DataValidationException(PrayerGroupErrorMessages.CANNOT_ADD_TO_PRIVATE_GROUP);
        }

        User user = entityManager.getReference(User.class, userId);
        PrayerGroupUser newPrayerGroupUser = new PrayerGroupUser(user, prayerGroup, PrayerGroupRole.MEMBER);

        PrayerGroupUser createdPrayerGroupUser = prayerGroupUserRepository.save(newPrayerGroupUser);
        return prayerGroupMapper.prayerGroupUserToPrayerGroupUserModel(createdPrayerGroupUser);
    }

    public void deletePrayerGroupUser(String authorizationHeader, int prayerGroupId, int userId) {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int submitterUserId = jwtService.extractUserId(token);

        if (submitterUserId != userId && getPrayerGroupRoleForUser(prayerGroupId, submitterUserId) != PrayerGroupRole.ADMIN) {
            throw new DataValidationException(PrayerGroupErrorMessages.MUST_BE_ADMIN_TO_REMOVE_USER);
        }

        PrayerGroupUser targetUserToDelete =
                prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId, userId)
                        .orElseThrow(() -> new DataValidationException(String.format(PrayerGroupErrorMessages.USER_DOES_NOT_BELONG_TO_PRAYER_GROUP, userId, prayerGroupId)));

        prayerGroupUserRepository.delete(targetUserToDelete);

    }

    public PrayerGroupUsersGetResponse getPrayerGroupUsers(int prayerGroupId, PrayerGroupUsersGetRequest request) {
        List<PrayerGroupUserDTO> prayerGroupUsers = prayerGroupRepository.getPrayerGroupUsers(prayerGroupId, request.getPrayerGroupRoles());
        List<PrayerGroupUserModel> prayerGroupUserModels = prayerGroupUsers.stream().map(prayerGroupMapper::prayerGroupUserDTOToPrayerGroupUserModel).toList();
        List<PrayerGroupUserModel> sortedPrayerGroupUsers = prayerGroupUserModels.stream().sorted((userA, userB) -> comparePrayerGroupUsers(userA, userB, request.getSortConfig())).toList();

        return new PrayerGroupUsersGetResponse(sortedPrayerGroupUsers);
    }

    public PrayerGroupUsersGetResponse updatePrayerGroupUsers(String authorizationHeader, int prayerGroupId, PrayerGroupUserUpdateRequest prayerGroupUserUpdateRequest) throws SQLException {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);

        if (!Objects.equals(getPrayerGroupRoleForUser(prayerGroupId, userId), PrayerGroupRole.ADMIN)) {
            throw new DataValidationException(PrayerGroupErrorMessages.MUST_BE_ADMIN_TO_UPDATE_USERS);
        }

        List<PrayerGroupUserUpdateModel> prayerGroupUserUpdateModels = prayerGroupUserUpdateRequest.getPrayerGroupUsers();

        Optional<PrayerGroupUserUpdateModel> adminUser = prayerGroupUserUpdateModels.stream()
                .filter(prayerGroupUser -> prayerGroupUser.getPrayerGroupRole() == PrayerGroupRole.ADMIN)
                .findFirst();

        if (adminUser.isEmpty()) {
            throw new DataValidationException(PrayerGroupErrorMessages.PRAYER_GROUP_MUST_HAVE_ADMIN);
        }

        PrayerGroupUserUpdateItem[] prayerGroupUserUpdateItems = mapPrayerGroupUserUpdateModelsToUpdateItems(prayerGroupId, prayerGroupUserUpdateModels);
        prayerGroupRepository.updatePrayerGroupUsers(prayerGroupId, prayerGroupUserUpdateItems);

        SortConfig<PrayerGroupUserSortField> sortConfig = new SortConfig<>(PrayerGroupUserSortField.USERNAME, SortDirection.ASCENDING);
        List<PrayerGroupRole> prayerGroupRoles = List.of(new PrayerGroupRole[]{PrayerGroupRole.ADMIN, PrayerGroupRole.MEMBER});

        PrayerGroupUsersGetRequest queryUsersRequest = new PrayerGroupUsersGetRequest(prayerGroupRoles, sortConfig);
        return getPrayerGroupUsers(prayerGroupId, queryUsersRequest);
    }

    public PrayerGroupSearchResponse searchPrayerGroups(PrayerGroupSearchRequest searchRequest) {
        int maxNumResults = Optional.ofNullable(searchRequest.getMaxNumResults()).orElse(20);
        List<PrayerGroupSummaryDTO> prayerGroupSummaryDTOS = prayerGroupRepository.searchPrayerGroups(searchRequest.getGroupNameQuery(), maxNumResults);
        List<PrayerGroupSummaryModel> prayerGroups = prayerGroupSummaryDTOS.stream().map(prayerGroupMapper::prayerGroupSummaryDTOToPrayerGroupSummaryModel).toList();
        return new PrayerGroupSearchResponse(prayerGroups);
    }

    public @Nullable PrayerGroupRole getPrayerGroupRoleForUser(int prayerGroupId, int userId) {
        Optional<PrayerGroupUser> prayerGroupUser =
                prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId,
                        userId);
        return prayerGroupUser.map(PrayerGroupUser::getPrayerGroupRole).orElse(null);

    }

    private PrayerGroupUserUpdateItem[] mapPrayerGroupUserUpdateModelsToUpdateItems(int prayerGroupId, List<PrayerGroupUserUpdateModel> prayerGroupUserUpdateModels) {
        return prayerGroupUserUpdateModels.stream().map(userUpdateModel -> {
            try {
                return new PrayerGroupUserUpdateItem(userUpdateModel.getUserId(), userUpdateModel.getPrayerGroupRole().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).toArray(PrayerGroupUserUpdateItem[]::new);
    }

    private boolean hasActiveJoinRequests(int prayerGroupId) {
        return joinRequestRepository.findByPrayerGroup_prayerGroupId(prayerGroupId).isPresent();
    }

    private int comparePrayerGroupUsers(PrayerGroupUserModel userModelA, PrayerGroupUserModel userModelB, SortConfig<PrayerGroupUserSortField> sortConfig) {
        int sortCoefficient = sortConfig.getSortDirection() == SortDirection.ASCENDING ? 1 : -1;

        return switch (sortConfig.getSortField()) {
            case PrayerGroupUserSortField.USERNAME ->
                    userModelA.getUsername().compareTo(userModelB.getUsername()) * sortCoefficient;
            case PrayerGroupUserSortField.FULL_NAME ->
                    userModelA.getFullName().compareTo(userModelB.getFullName()) * sortCoefficient;
        };
    }
}