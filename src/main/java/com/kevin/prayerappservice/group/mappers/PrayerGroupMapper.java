package com.kevin.prayerappservice.group.mappers;

import com.kevin.prayerappservice.file.entities.FileType;
import com.kevin.prayerappservice.file.entities.MediaFile;
import com.kevin.prayerappservice.group.constants.JoinStatus;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupSummaryDTO;
import com.kevin.prayerappservice.group.models.PrayerGroupModel;
import com.kevin.prayerappservice.group.models.PrayerGroupSummaryModel;
import com.kevin.prayerappservice.group.models.PrayerGroupUserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
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
    PrayerGroupModel createdPrayerGroupDTOToPrayerGroupModel(CreatedPrayerGroupDTO source);

    @Mapping(source = "mediaFileId", target = "avatarFile.mediaFileId")
    @Mapping(source = "fileName", target = "avatarFile.fileName")
    @Mapping(source = "fileUrl", target = "avatarFile.fileUrl")
    @Mapping(source = "fileType", target = "avatarFile.fileType")
    PrayerGroupSummaryModel prayerGroupSummaryDTOToPrayerGroupSummaryModel(PrayerGroupSummaryDTO source);

    @Mapping(source = "avatarFileId", target = "avatarFile.mediaFileId")
    @Mapping(source = "avatarFileName", target = "avatarFile.fileName")
    @Mapping(source = "avatarFileUrl", target = "avatarFile.fileUrl")
    @Mapping(source = "avatarFileType", target = "avatarFile.fileType")
    @Mapping(source = "bannerFileId", target = "bannerFile.mediaFileId")
    @Mapping(source = "bannerFileName", target = "bannerFile.fileName")
    @Mapping(source = "bannerFileUrl", target = "bannerFile.fileUrl")
    @Mapping(source = "bannerFileType", target = "bannerFile.fileType")
    @Mapping(source = ".", target = "userJoinStatus")
    PrayerGroupModel prayerGroupDTOToPrayerGroupModel(PrayerGroupDTO prayerGroupDTO);

    @AfterMapping
    default void setImagesToNull(@MappingTarget PrayerGroupModel prayerGroupModel) {
        MediaFile avatarMediaFile = prayerGroupModel.getAvatarFile();
        MediaFile bannerMediaFile = prayerGroupModel.getBannerFile();

        if (avatarMediaFile.getMediaFileId() == null) {
            prayerGroupModel.setAvatarFile(null);
        }

        if (bannerMediaFile.getMediaFileId() == null) {
            prayerGroupModel.setBannerFile(null);
        }
    }

    @AfterMapping
    default void setAvatarImageToNull(@MappingTarget PrayerGroupSummaryModel prayerGroupSummaryModel){
        MediaFile avatarMediaFile = prayerGroupSummaryModel.getAvatarFile();

        if(avatarMediaFile.getMediaFileId() == null){
            prayerGroupSummaryModel.setAvatarFile(null);
        }
    }

    default List<PrayerGroupUserModel> mapToPrayerGroupUsers(CreatedPrayerGroupDTO source) {
        MediaFile adminImage = null;

        if (source.getAdminImageFileId() != null) {
            adminImage = new MediaFile(source.getAdminImageFileName(), FileType.IMAGE,
                    source.getAdminImageFileUrl());
            adminImage.setMediaFileId(source.getAdminImageFileId());
        }


        PrayerGroupUserModel[] prayerGroupUsers =
                new PrayerGroupUserModel[]{new PrayerGroupUserModel(source.getAdminUserId(), null,
                        null, source.getAdminFullName(), null, adminImage, PrayerGroupRole.ADMIN)};
        return List.of(prayerGroupUsers);
    }

    default JoinStatus mapJoinStatus(PrayerGroupDTO prayerGroupDTO){
        if(prayerGroupDTO.getPrayerGroupRole() != null){
            return JoinStatus.JOINED;
        }

        if(prayerGroupDTO.getJoinRequestId() != null){
            return JoinStatus.REQUEST_SUBMITTED;
        }

        return JoinStatus.NOT_JOINED;
    }
}
