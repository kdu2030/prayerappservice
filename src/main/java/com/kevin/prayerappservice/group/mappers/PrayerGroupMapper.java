package com.kevin.prayerappservice.group.mappers;

import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import com.kevin.prayerappservice.group.models.PrayerGroupUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PrayerGroupMapper {
    @Mapping(source = "avatarFileId", target = "avatarFile.mediaFileId")
    @Mapping(source = "groupAvatarFileName", target = "avatarFile.fileName")
    @Mapping(source = "groupAvatarFileUrl", target = "avatarFile.fileUrl")
    @Mapping(constant = "IMAGE", target = "avatarFile.fileType")
    @Mapping(source = "bannerFileId", target = "bannerFile.mediaFileId")
    @Mapping(source = "groupBannerFileName", target = "bannerFile.fileName")
    @Mapping(source = "groupBannerFileUrl", target = "bannerFile.fileUrl")
    @Mapping(constant = "IMAGE", target = "bannerFile.fileType")
    @Mapping(source = "source", target = "admins")
    @Mapping(constant = "JOINED", target = "userJoinStatus")
    @Mapping(constant = "ADMIN", target = "prayerGroupRole")
    PrayerGroupModel createdPrayerGroupDTOToPrayerGroupModel(CreatedPrayerGroupDTO createdPrayerGroupDTO);

    default List<PrayerGroupUserModel> mapToPrayerGroupUsers(CreatedPrayerGroupDTO createdPrayerGroupDTO) {
        MediaFile adminImage = new MediaFile(createdPrayerGroupDTO.getAdminImageFileName(), FileType.IMAGE,
                createdPrayerGroupDTO.getAdminImageFileUrl());
        adminImage.setMediaFileId(createdPrayerGroupDTO.getAdminImageFileId());

        PrayerGroupUserModel[] prayerGroupUsers =
                new PrayerGroupUserModel[]{new PrayerGroupUserModel(createdPrayerGroupDTO.getAdminUserId(), null,
                        null, createdPrayerGroupDTO.getAdminFullName(), null, adminImage, PrayerGroupRole.ADMIN)};
        return List.of(prayerGroupUsers);
    }
}
