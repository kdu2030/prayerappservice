package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.dtos.*;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.group.mappers.PrayerGroupMapper;
import com.kevin.prayerappservice.group.models.*;
import com.kevin.prayerappservice.request.JoinRequestRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .findByPrayerGroupIdAndUserId(prayerGroupId, userId)
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
                putPrayerGroupRequest.getDescription(), putPrayerGroupRequest.getRules(), visibilityLevel, avatarFile, bannerFile);

        prayerGroupRepository.save(updatedPrayerGroup);

        return getPrayerGroup(authorizationHeader, prayerGroupId);
    }

    public boolean hasActiveJoinRequests(int prayerGroupId) {
        return joinRequestRepository.findByPrayerGroupId(prayerGroupId).isPresent();
    }
}
