package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
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
    public ResponseEntity<PrayerRequestModel> createPrayerRequest(String authToken, int prayerGroupId, PrayerRequestCreateRequest prayerRequestCreateRequest){
        PrayerRequestModel prayerRequestModel = prayerRequestService.createPrayerRequest(authToken, prayerGroupId, prayerRequestCreateRequest);
        return new ResponseEntity<>(prayerRequestModel, HttpStatus.OK);
    }

}
