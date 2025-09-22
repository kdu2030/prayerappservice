package com.kevin.prayerappservice.join.mappers;

import com.kevin.prayerappservice.join.entities.JoinRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JoinRequestMapper {
    @Mapping(source = "prayerGroup.prayerGroupId", target = "prayerGroupId")
    @Mapping(source = "user.userId", target = "user.userId")
    @Mapping(source  = "user.fullName", target = "user.fullName")
    @Mapping(source = "user.imageFile.mediaFileId", target = "user.image.mediaFileId")
    JoinRequestModel joinRequestToJoinRequestModel(JoinRequest joinRequest);
}
