package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.constants.PrayerGroupErrorMessages;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.join.entities.JoinRequest;
import com.kevin.prayerappservice.join.models.JoinRequestCreateRequest;
import com.kevin.prayerappservice.join.models.JoinRequestModel;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final PrayerGroupRepository prayerGroupRepository;
    private final EntityManager entityManager;

    public JoinRequestService(JoinRequestRepository joinRequestRepository, PrayerGroupRepository prayerGroupRepository, EntityManager entityManager) {
        this.joinRequestRepository = joinRequestRepository;
        this.prayerGroupRepository = prayerGroupRepository;
        this.entityManager = entityManager;
    }

    public void createJoinRequest(int prayerGroupId, JoinRequestCreateRequest joinRequestCreateRequest){
        Optional<PrayerGroup> prayerGroupResponse = prayerGroupRepository.findById(prayerGroupId);
        if(prayerGroupResponse.isEmpty()){
            throw new DataValidationException(String.format(PrayerGroupErrorMessages.CANNOT_FIND_PRAYER_GROUP, prayerGroupId));
        }

        User user = entityManager.getReference(User.class, joinRequestCreateRequest.getUserId());
        JoinRequest joinRequest = new JoinRequest(user, prayerGroupResponse.get(), joinRequestCreateRequest.getSubmittedDate());
        joinRequestRepository.save(joinRequest);

    }
}
