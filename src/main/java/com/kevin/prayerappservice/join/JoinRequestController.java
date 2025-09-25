package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class JoinRequestController implements JoinRequestApi {
    private final JoinRequestService joinRequestService;

    public JoinRequestController(JoinRequestService joinRequestService){
        this.joinRequestService = joinRequestService;
    }

    public ResponseEntity<JoinRequestModel> createJoinRequest(int prayerGroupId, JoinRequestCreateRequest createRequest){
        JoinRequestModel joinRequestModel = joinRequestService.createJoinRequest(prayerGroupId, createRequest);
        return new ResponseEntity<>(joinRequestModel, HttpStatus.OK);
    }

    public ResponseEntity<JoinRequestsGetResponse> getJoinRequests(int prayerGroupId, JoinRequestsGetRequest getRequest){
        JoinRequestsGetResponse joinRequestsGetResponse = joinRequestService.getJoinRequests(prayerGroupId, getRequest);
        return new ResponseEntity<>(joinRequestsGetResponse, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteJoinRequests(String authToken, int prayerGroupId, JoinRequestDeleteRequest deleteRequest){
        joinRequestService.deleteJoinRequests(authToken, prayerGroupId, deleteRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> approveJoinRequests(String authToken, int prayerGroupId, JoinRequestApproveRequest approveRequest){
        joinRequestService.approveJoinRequests(authToken, prayerGroupId, approveRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
