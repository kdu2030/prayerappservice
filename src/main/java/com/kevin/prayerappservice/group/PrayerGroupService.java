package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.PrayerGroupErrorMessages;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.dtos.*;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.group.mappers.PrayerGroupMapper;
import com.kevin.prayerappservice.group.models.*;
import com.kevin.prayerappservice.request.JoinRequestRepository;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
                new PrayerGroupRole[]{PrayerGroupRole.ADMIN});

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

        if (prayerGroup.getVisibilityLevel() == VisibilityLevel.PRIVATE) {
            throw new DataValidationException(PrayerGroupErrorMessages.CANNOT_ADD_TO_PRIVATE_GROUP);
        }

        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int submitterUserId = jwtService.extractUserId(token);

        if (submitterUserId != userId && getPrayerGroupRoleForUser(prayerGroupId, submitterUserId) != PrayerGroupRole.ADMIN) {
            throw new DataValidationException(PrayerGroupErrorMessages.MUST_BE_ADMIN_TO_ADD);
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

    public void updatePrayerGroupUsers(String authorizationHeader, int prayerGroupId, PrayerGroupUserUpdateRequest prayerGroupUserUpdateRequest) throws SQLException {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);

        if (!Objects.equals(getPrayerGroupRoleForUser(prayerGroupId, userId), PrayerGroupRole.ADMIN)) {
            throw new DataValidationException(PrayerGroupErrorMessages.MUST_BE_ADMIN_TO_UPDATE_USERS);
        }

        List<PrayerGroupUserUpdateModel> prayerGroupUserUpdateModels = prayerGroupUserUpdateRequest.getPrayerGroupUsers();

        Optional<PrayerGroupUserUpdateModel> adminUser = prayerGroupUserUpdateModels.stream()
                .filter(prayerGroupUser -> prayerGroupUser.getPrayerGroupRole() == PrayerGroupRole.ADMIN)
                .findFirst();

        if(adminUser.isEmpty()){
            throw new DataValidationException(PrayerGroupErrorMessages.PRAYER_GROUP_MUST_HAVE_ADMIN);
        }

        PrayerGroupUserUpdateItem[] prayerGroupUserUpdateItems = mapPrayerGroupUserUpdateModelsToUpdateItems(prayerGroupId, prayerGroupUserUpdateModels);
        prayerGroupRepository.updatePrayerGroupUsers(prayerGroupUserUpdateItems);
    }

    private @Nullable PrayerGroupRole getPrayerGroupRoleForUser(int prayerGroupId, int userId) {
        Optional<PrayerGroupUser> prayerGroupUser =
                prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId,
                        userId);
        return prayerGroupUser.map(PrayerGroupUser::getPrayerGroupRole).orElse(null);

    }

    private PrayerGroupUserUpdateItem[] mapPrayerGroupUserUpdateModelsToUpdateItems(int prayerGroupId, List<PrayerGroupUserUpdateModel> prayerGroupUserUpdateModels){
        return (PrayerGroupUserUpdateItem[]) prayerGroupUserUpdateModels.stream().map(userUpdateModel -> {
            try {
                return new PrayerGroupUserUpdateItem(userUpdateModel.getUserId(), prayerGroupId, userUpdateModel.getPrayerGroupRole().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).toArray();
    }

    private boolean hasActiveJoinRequests(int prayerGroupId) {
        return joinRequestRepository.findByPrayerGroup_prayerGroupId(prayerGroupId).isPresent();
    }
}