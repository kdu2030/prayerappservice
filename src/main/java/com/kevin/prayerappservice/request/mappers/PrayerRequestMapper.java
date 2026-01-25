package com.kevin.prayerappservice.request.mappers;

import com.kevin.prayerappservice.request.dtos.PrayerRequestCommentResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetResult;
import com.kevin.prayerappservice.request.entities.PrayerRequestBookmark;
import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import com.kevin.prayerappservice.request.models.*;
import org.mapstruct.*;

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
    @Mapping(ignore = true, target = "userLikeId")
    @Mapping(ignore = true, target = "userCommentId")
    @Mapping(ignore = true, target = "userBookmarkId")
    @Mapping(ignore = true, target = "userPrayerSessionId")
    @Mapping(constant = "0", target = "likeCount")
    @Mapping(constant = "0", target = "prayedCount")
    @Mapping(constant = "0", target = "commentCount")
    PrayerRequestModel prayerRequestCreateResultToPrayerRequestModel(PrayerRequestCreateResult source);

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
    PrayerRequestModel prayerRequestGetResultToPrayerRequestModel(PrayerRequestGetResult source);

    @Mapping(source = "prayerRequest.prayerRequestId", target = "prayerRequestId")
    @Mapping(source = "user.userId", target = "submittedUserId")
    PrayerRequestLikeModel prayerRequestLikeToPrayerRequestLikeModel(PrayerRequestLike source);

    @Mapping(source = "prayerRequest.prayerRequestId", target = "prayerRequestId")
    @Mapping(source = "user.userId", target = "submittedUserId")
    PrayerRequestBookmarkModel prayerRequestBookmarkToModel(PrayerRequestBookmark source);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "fullName", target = "user.fullName")
    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "imageFileId", target = "user.image.mediaFileId")
    @Mapping(source = "userFileName", target = "")
    PrayerRequestCommentModel prayerRequestCommentToModel(PrayerRequestCommentResult source);

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
