package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.models.JoinRequestModel;

public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
    }


}
