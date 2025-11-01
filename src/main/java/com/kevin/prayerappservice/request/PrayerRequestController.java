package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestFilterCriteria;
import com.kevin.prayerappservice.request.models.PrayerRequestGetResponse;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

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

}
