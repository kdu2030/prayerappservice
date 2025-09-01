package com.kevin.prayerappservice.service;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.group.PrayerGroupJdbcRepositoryImpl;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupService;
import com.kevin.prayerappservice.group.constants.JoinStatus;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupUserDTO;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.mappers.PrayerGroupMapper;
import com.kevin.prayerappservice.group.models.CreatePrayerGroupRequest;
import com.kevin.prayerappservice.group.models.GroupNameValidationResponse;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import com.kevin.prayerappservice.group.models.PrayerGroupUserModel;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class PrayerGroupServiceTests {
    @MockBean
    private PrayerGroupJdbcRepositoryImpl mockPrayerGroupJdbcRepository;

    @Autowired
    private PrayerGroupRepository prayerGroupRepository;

    @MockBean
    private JwtService mockJwtService;

    @Autowired
    private PrayerGroupMapper prayerGroupMapper;

    @Autowired
    private PrayerGroupService prayerGroupService;

    @Test
    @DirtiesContext
    public void validateGroupName_nameIsDuplicate_returnsError() {
        PrayerGroup mockPrayerGroup = new PrayerGroup("Autobots", "Robots in disguise", null, VisibilityLevel.PUBLIC,
                null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        GroupNameValidationResponse response = prayerGroupService.validateGroupName("Autobots");

        Assertions.assertThat(response.getErrors()[0]).isEqualTo("A prayer group with this name already exists.");
    }

    @Test
    @DirtiesContext
    public void validateGroupName_nameIsUnique_doesNotReturnError() {
        PrayerGroup mockPrayerGroup = new PrayerGroup("Autobots", "Robots in disguise", null, VisibilityLevel.PUBLIC,
                null, null);
        prayerGroupRepository.save(mockPrayerGroup);

        GroupNameValidationResponse response = prayerGroupService.validateGroupName("Autos");
        Assertions.assertThat(response.getIsNameValid()).isTrue();
        Assertions.assertThat(response.getErrors()).isNullOrEmpty();
    }

    @Test
    public void createPrayerGroup_givenValidPrayerGroup_returnsGroupModel() {
        CreatedPrayerGroupDTO createdPrayerGroupDTO = new CreatedPrayerGroupDTO(737, "河北大学", "河北大学学生小组", null,
                VisibilityLevel.PRIVATE, 757, "hebeidaxue.png", "https://prayerappfileservices.pythonanywhere" +
                ".com/static/test.png", 767, "hebeidaxue_zhaopian.png", "https://prayerappfileservices.pythonanywhere" +
                ".static/test.png", 80, "王明", null, null, null);

        CreatePrayerGroupRequest request = new CreatePrayerGroupRequest(createdPrayerGroupDTO.getGroupName(),
                createdPrayerGroupDTO.getDescription(), createdPrayerGroupDTO.getRules(),
                createdPrayerGroupDTO.getAvatarFileId(), createdPrayerGroupDTO.getBannerFileId(),
                createdPrayerGroupDTO.getVisibilityLevel());

        Mockito.when(mockPrayerGroupJdbcRepository.createPrayerGroup(any(CreatePrayerGroupRequestDTO.class))).thenReturn(createdPrayerGroupDTO);
        Mockito.when(mockJwtService.extractUserId(anyString())).thenReturn(createdPrayerGroupDTO.getAdminUserId());

        PrayerGroupModel prayerGroupModel = prayerGroupService.createPrayerGroup("mockAuthHeader", request);

        Assertions.assertThat(prayerGroupModel.getPrayerGroupId()).isEqualTo(createdPrayerGroupDTO.getPrayerGroupId());
        Assertions.assertThat(prayerGroupModel.getGroupName()).isEqualTo(createdPrayerGroupDTO.getGroupName());
        Assertions.assertThat(prayerGroupModel.getAdmins().get(0).getUserId()).isEqualTo(createdPrayerGroupDTO.getAdminUserId());
    }

    @Test
    public void createPrayerGroup_givenPrayerGroupWithNullableFields_returnsGroupModel() {
        CreatedPrayerGroupDTO createdPrayerGroupDTO = new CreatedPrayerGroupDTO(777, "Top Gear", null, null,
                VisibilityLevel.PUBLIC, null, null, null, null, null, null, 80, "James May", null, null, null);

        CreatePrayerGroupRequest request = new CreatePrayerGroupRequest(createdPrayerGroupDTO.getGroupName(),
                createdPrayerGroupDTO.getDescription(), createdPrayerGroupDTO.getRules(),
                createdPrayerGroupDTO.getAvatarFileId(), createdPrayerGroupDTO.getBannerFileId(),
                createdPrayerGroupDTO.getVisibilityLevel());

        Mockito.when(mockPrayerGroupJdbcRepository.createPrayerGroup(any(CreatePrayerGroupRequestDTO.class))).thenReturn(createdPrayerGroupDTO);
        Mockito.when(mockJwtService.extractUserId(anyString())).thenReturn(createdPrayerGroupDTO.getAdminUserId());

        PrayerGroupModel prayerGroupModel = prayerGroupService.createPrayerGroup("mockAuthHeader", request);

        Assertions.assertThat(prayerGroupModel.getPrayerGroupId()).isEqualTo(createdPrayerGroupDTO.getPrayerGroupId());
        Assertions.assertThat(prayerGroupModel.getBannerFile()).isNull();
        Assertions.assertThat(prayerGroupModel.getAvatarFile()).isNull();
    }

    @Test
    public void getPrayerGroup_givenPrayerGroupId_returnsPrayerGroupModel() {
        PrayerGroupDTO mockPrayerGroup = new PrayerGroupDTO(6116, "Naboo Delegation", "Senator Amidala and " +
                "Representative Binks", null, VisibilityLevel.PRIVATE.toString(), 34, "naboo.png", "https" +
                "://prayerappfileservices.pythonanywhere.com/test.png", FileType.IMAGE.toString(), null, null, null,
                null, PrayerGroupRole.MEMBER.toString(), null);

        Mockito.when(mockPrayerGroupJdbcRepository.getPrayerGroup(anyInt(), anyInt())).thenReturn(mockPrayerGroup);
        Mockito.when(mockJwtService.extractUserId(anyString())).thenReturn(1409);

        PrayerGroupModel prayerGroupModel = prayerGroupService.getPrayerGroup("Bearer testToken", 6116);

        Assertions.assertThat(prayerGroupModel.getPrayerGroupId()).isEqualTo(mockPrayerGroup.getPrayerGroupId());
        Assertions.assertThat(prayerGroupModel.getAvatarFile().getFileUrl()).isEqualTo(mockPrayerGroup.getAvatarFileUrl());
        Assertions.assertThat(prayerGroupModel.getUserJoinStatus()).isEqualTo(JoinStatus.JOINED);
    }

    @Test
    public void getPrayerGroup_givenJoinRequestId_returnsRequestSubmittedJoinStatus() {
        PrayerGroupDTO mockPrayerGroup = new PrayerGroupDTO(6116, "Naboo Delegation", "Senator Amidala and " +
                "Representative Binks", null, VisibilityLevel.PRIVATE.toString(), 34, "naboo.png", "https" +
                "://prayerappfileservices.pythonanywhere.com/test.png", FileType.IMAGE.toString(), null, null, null,
                null, null, 1004);

        Mockito.when(mockPrayerGroupJdbcRepository.getPrayerGroup(anyInt(), anyInt())).thenReturn(mockPrayerGroup);
        Mockito.when(mockJwtService.extractUserId(anyString())).thenReturn(1409);

        PrayerGroupModel prayerGroupModel = prayerGroupService.getPrayerGroup("Bearer testToken", 6116);

        Assertions.assertThat(prayerGroupModel.getUserJoinStatus()).isEqualTo(JoinStatus.REQUEST_SUBMITTED);
    }

    @Test
    public void getPrayerGroup_givenValidId_returnsAdmins() {
        PrayerGroupDTO mockPrayerGroup = new PrayerGroupDTO(6116, "Naboo Delegation", "Senator Amidala and " +
                "Representative Binks", null, VisibilityLevel.PRIVATE.toString(), 34, "naboo.png", "https" +
                "://prayerappfileservices.pythonanywhere.com/test.png", FileType.IMAGE.toString(), null, null, null,
                null, null, 1004);

        PrayerGroupUserDTO[] prayerGroupUserDTOS = new PrayerGroupUserDTO[]{
                new PrayerGroupUserDTO(320, "Padme Amidala", "pamidala", PrayerGroupRole.ADMIN.toString(), null, null
                        , null, null),
                new PrayerGroupUserDTO(380, "Jar Jar Binks", "jbinks", PrayerGroupRole.ADMIN.toString(), 330,
                        "jarjar_binks.jpeg", "https://prayerappfileservices.pythonanywhere.com/45.png",
                        FileType.IMAGE.toString())
        };

        Mockito.when(mockPrayerGroupJdbcRepository.getPrayerGroup(anyInt(), anyInt())).thenReturn(mockPrayerGroup);
        Mockito.when(mockPrayerGroupJdbcRepository.getPrayerGroupUsers(anyInt(), any(PrayerGroupRole[].class))).thenReturn(List.of(prayerGroupUserDTOS));
        Mockito.when(mockJwtService.extractUserId(anyString())).thenReturn(1409);

        PrayerGroupModel prayerGroupModel = prayerGroupService.getPrayerGroup("Bearer testToken", 6116);

        List<PrayerGroupUserModel> admins = prayerGroupModel.getAdmins();

        Assertions.assertThat(admins.getFirst().getUserId()).isEqualTo(prayerGroupUserDTOS[0].getUserId());
        Assertions.assertThat(admins.get(1).getUserId()).isEqualTo(prayerGroupUserDTOS[1].getUserId());
    }


}
