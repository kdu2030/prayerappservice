package com.kevin.prayerappservice.join.mappers;

import com.kevin.prayerappservice.file.models.MediaFileSummary;
import com.kevin.prayerappservice.join.dtos.JoinRequestDTO;
import com.kevin.prayerappservice.join.entities.JoinRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import com.kevin.prayerappservice.user.models.UserSummary;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JoinRequestMapper {
    @Mapping(source = "prayerGroup.prayerGroupId", target = "prayerGroupId")
    @Mapping(source = "user.userId", target = "user.userId")
    @Mapping(source  = "user.fullName", target = "user.fullName")
    @Mapping(source = "user.imageFile.mediaFileId", target = "user.image.mediaFileId")
    JoinRequestModel joinRequestToJoinRequestModel(JoinRequest joinRequest);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "fullName", target = "user.fullName")
    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "mediaFileId", target = "user.image.mediaFileId")
    @Mapping(source = "fileName", target = "user.image.fileName")
    @Mapping(source = "fileUrl", target = "user.image.fileUrl")
    @Mapping(source = "fileType", target = "user.image.fileType")
    JoinRequestModel joinRequestDTOToJoinRequestModel(JoinRequestDTO joinRequest);

    @AfterMapping
    default void setUserImageToNull(@MappingTarget JoinRequestModel joinRequestModel){
        UserSummary user = joinRequestModel.getUser();
        MediaFileSummary userImage = user.getImage();

        if(userImage == null){
            return;
        }

        if(userImage.getMediaFileId() == null){
            user.setImage(null);
        }
    }
}
