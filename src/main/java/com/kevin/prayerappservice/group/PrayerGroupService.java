package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.mappers.PrayerGroupMapper;
import com.kevin.prayerappservice.group.models.CreatePrayerGroupRequest;
import com.kevin.prayerappservice.group.models.GroupNameValidationResponse;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrayerGroupService {
    private final PrayerGroupRepository prayerGroupRepository;
    private final JwtService jwtService;
    private final PrayerGroupMapper prayerGroupMapper;

    public PrayerGroupService(PrayerGroupRepository prayerGroupRepository, JwtService jwtService, PrayerGroupMapper prayerGroupMapper) {
        this.prayerGroupRepository = prayerGroupRepository;
        this.jwtService = jwtService;
        this.prayerGroupMapper = prayerGroupMapper;
    }

    public PrayerGroupModel createPrayerGroup(String authorizationHeader, CreatePrayerGroupRequest prayerGroupRequest) {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);
        VisibilityLevel visibilityLevel =
                Optional.ofNullable(prayerGroupRequest.getVisibilityLevel()).orElse(VisibilityLevel.PUBLIC);

        CreatePrayerGroupRequestDTO createPrayerGroupRequestDTO = new CreatePrayerGroupRequestDTO(userId,
                prayerGroupRequest.getGroupName(), prayerGroupRequest.getDescription(), prayerGroupRequest.getRules(),
                visibilityLevel.toString(), prayerGroupRequest.getAvatarFileId(), prayerGroupRequest.getBannerFileId());

       CreatedPrayerGroupDTO createdPrayerGroupDTO =  prayerGroupRepository.createPrayerGroup(createPrayerGroupRequestDTO);
       return prayerGroupMapper.createdPrayerGroupDTOToPrayerGroupModel(createdPrayerGroupDTO);
    }

    public GroupNameValidationResponse validateGroupName(String groupName){
        boolean isNameValid = true;
        String[] errors = null;
        Optional<PrayerGroup> prayerGroup = prayerGroupRepository.findByGroupName(groupName);

        if(prayerGroup.isPresent()){
            isNameValid = false;
            errors = new String[] {"A prayer group with this name already exists."};
        }

        return new GroupNameValidationResponse(isNameValid, errors);
    }

    public void getPrayerGroupSummariesByUser(int userId){
        prayerGroupRepository.getPrayerGroupSummariesByUserId(userId);
    }
}
