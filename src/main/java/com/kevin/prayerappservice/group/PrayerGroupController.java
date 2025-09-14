package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.group.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;

@Controller
public class PrayerGroupController implements PrayerGroupApi {
    private final PrayerGroupService prayerGroupService;

    public PrayerGroupController(PrayerGroupService prayerGroupService){
        this.prayerGroupService = prayerGroupService;
    }

    @Override
    public ResponseEntity<PrayerGroupModel> createPrayerGroup(String authorizationHeader, CreatePrayerGroupRequest createPrayerGroupRequest){
        PrayerGroupModel prayerGroupModel = prayerGroupService.createPrayerGroup(authorizationHeader, createPrayerGroupRequest);
        return new ResponseEntity<>(prayerGroupModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupNameValidationResponse> validateGroupName(String name) {
        GroupNameValidationResponse validationResponse = prayerGroupService.validateGroupName(name);
        return new ResponseEntity<>(validationResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerGroupModel> getPrayerGroup(String authorizationHeader, int prayerGroupId){
        PrayerGroupModel prayerGroupModel = prayerGroupService.getPrayerGroup(authorizationHeader, prayerGroupId);
        return new ResponseEntity<>(prayerGroupModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerGroupModel> updatePrayerGroup(String authorizationHeader, int prayerGroupId, PutPrayerGroupRequest putPrayerGroupRequest){
        PrayerGroupModel prayerGroupModel = prayerGroupService.updatePrayerGroup(authorizationHeader, prayerGroupId, putPrayerGroupRequest);
        return new ResponseEntity<>(prayerGroupModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerGroupUserModel> addPrayerGroupUser(String authorizationHeader, int prayerGroupId, int userId){
        PrayerGroupUserModel prayerGroupUser = prayerGroupService.addPrayerGroupUser(authorizationHeader, prayerGroupId, userId);
        return new ResponseEntity<>(prayerGroupUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deletePrayerGroupUser(String authorizationHeader, int prayerGroupId, int userId){
        prayerGroupService.deletePrayerGroupUser(authorizationHeader, prayerGroupId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PrayerGroupUsersGetResponse> getPrayerGroupUsers(int prayerGroupId, PrayerGroupUsersGetRequest request){
        PrayerGroupUsersGetResponse prayerGroupUsersGetResponse = prayerGroupService.getPrayerGroupUsers(prayerGroupId,request);
        return new ResponseEntity<>(prayerGroupUsersGetResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updatePrayerGroupUsers(String authorizationHeader, int prayerGroupId, PrayerGroupUserUpdateRequest prayerGroupUserUpdateRequest) throws SQLException {
        prayerGroupService.updatePrayerGroupUsers(authorizationHeader, prayerGroupId, prayerGroupUserUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
