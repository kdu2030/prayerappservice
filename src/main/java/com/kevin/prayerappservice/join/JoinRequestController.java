package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.models.JoinRequestCreateRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import com.kevin.prayerappservice.join.models.JoinRequestsGetRequest;
import com.kevin.prayerappservice.join.models.JoinRequestsGetResponse;
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
}
