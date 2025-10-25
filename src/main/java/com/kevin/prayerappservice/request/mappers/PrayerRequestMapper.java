package com.kevin.prayerappservice.request.mappers;

import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;
import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
import com.kevin.prayerappservice.request.models.PrayerRequestPrayerGroupSummary;
import com.kevin.prayerappservice.request.models.PrayerRequestUserSummary;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PrayerRequestMapper {
    @Mapping(source = "prayerGroupId", target = "prayerGroup.prayerGroupId")
    @Mapping(source = "groupName", target = "prayerGroup.groupName")
    @Mapping(source = "avatarFileId", target = "prayerGroup.avatarFile.mediaFileId")
    @Mapping(source = "avatarFileName", target = "prayerGroup.avatarFile.fileName")
    @Mapping(source = "avatarFileUrl", target = "prayerGroup.avatarFile.fileUrl")
    @Mapping(source = "avatarFileType", target = "prayerGroup.avatarFile.fileType")
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "fullName", target = "user.fullName")
    @Mapping(source = "userFileId", target = "user.image.mediaFileId")
    @Mapping(source = "userFileName", target = "user.image.fileName")
    @Mapping(source = "userFileUrl", target = "user.image.fileUrl")
    @Mapping(source = "userFileType", target = "user.image.fileType")
    @Mapping(constant = "false", target = "userPrayed")
    @Mapping(constant = "false", target = "userCommented")
    @Mapping(constant = "false", target = "userLiked")
    @Mapping(constant = "0", target = "likeCount")
    @Mapping(constant = "0", target = "prayedCount")
    @Mapping(constant = "0", target = "commentCount")
    PrayerRequestModel prayerRequestCreateResultToPrayerRequestModel(PrayerRequestCreateResult source);

    @AfterMapping
    default void setImagesToNull(@MappingTarget PrayerRequestModel prayerRequestModel){
        PrayerRequestPrayerGroupSummary prayerGroupSummary = prayerRequestModel.getPrayerGroup();
        PrayerRequestUserSummary prayerRequestUserSummary = prayerRequestModel.getUser();

        if(prayerGroupSummary.getAvatarFile().getMediaFileId() == null){
            prayerGroupSummary.setAvatarFile(null);
        }

        if(prayerRequestUserSummary.getImage().getMediaFileId() == null){
            prayerRequestUserSummary.setImage(null);
        }
    }
}
