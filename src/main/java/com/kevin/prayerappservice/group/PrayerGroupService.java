package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.entities.VisibilityLevel;
import com.kevin.prayerappservice.group.models.CreatePrayerGroupRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrayerGroupService {
    private final PrayerGroupRepository prayerGroupRepository;
    private final JwtService jwtService;

    public PrayerGroupService(PrayerGroupRepository prayerGroupRepository, JwtService jwtService) {
        this.prayerGroupRepository = prayerGroupRepository;
        this.jwtService = jwtService;
    }

    public void createPrayerGroup(String authorizationHeader, CreatePrayerGroupRequest prayerGroupRequest) {
        String token = jwtService.extractTokenFromAuthHeader(authorizationHeader);
        int userId = jwtService.extractUserId(token);
        VisibilityLevel visibilityLevel = Optional.ofNullable(prayerGroupRequest.getVisibilityLevel()).orElse(VisibilityLevel.PUBLIC);

        CreatePrayerGroupRequestDTO createPrayerGroupRequestDTO = new CreatePrayerGroupRequestDTO(userId,
                prayerGroupRequest.getGroupName(), prayerGroupRequest.getDescription(), prayerGroupRequest.getRules(),
                visibilityLevel.toString(), prayerGroupRequest.getAvatarFileId(), prayerGroupRequest.getBannerFileId());

        prayerGroupRepository.createPrayerGroup(createPrayerGroupRequestDTO);

    }

}
