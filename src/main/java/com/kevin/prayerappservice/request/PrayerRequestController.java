package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PrayerRequestController implements PrayerRequestApi {
    public final PrayerRequestService prayerRequestService;

    public PrayerRequestController(PrayerRequestService prayerRequestService){
        this.prayerRequestService = prayerRequestService;
    }

    @Override
    public ResponseEntity<PrayerRequestModel> createPrayerRequest(String authToken, PrayerRequestCreateRequest prayerRequestCreateRequest){
        PrayerRequestModel prayerRequestModel = prayerRequestService.createPrayerRequest(authToken, prayerRequestCreateRequest);
        return new ResponseEntity<>(prayerRequestModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerRequestGetResponse> getPrayerRequests(String authHeader, PrayerRequestFilterCriteria prayerRequestFilterCriteria){
        PrayerRequestGetResponse getResponse = prayerRequestService.getPrayerRequests(authHeader, prayerRequestFilterCriteria);
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PrayerRequestLikeModel> createPrayerRequestLike(int prayerRequestId, PrayerRequestActionCreateRequest createRequest){
        PrayerRequestLikeModel prayerRequestLike = prayerRequestService.createPrayerRequestLike(prayerRequestId, createRequest);
        return new ResponseEntity<>(prayerRequestLike, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deletePrayerRequestLike(String authHeader, int prayerRequestLikeId){
        prayerRequestService.deletePrayerRequestLike(authHeader, prayerRequestLikeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
