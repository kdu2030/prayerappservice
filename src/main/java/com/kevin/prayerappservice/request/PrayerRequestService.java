package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupRepository;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.VisibilityLevel;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.request.constants.PrayerRequestErrors;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetResult;
import com.kevin.prayerappservice.request.mappers.PrayerRequestMapper;
import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestFilterCriteria;
import com.kevin.prayerappservice.request.models.PrayerRequestGetResponse;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrayerRequestService {
    private final JwtService jwtService;
    private final PrayerGroupRepository prayerGroupRepository;
    private final PrayerGroupUserRepository prayerGroupUserRepository;
    private final PrayerRequestRepository prayerRequestRepository;
    private final PrayerRequestMapper prayerRequestMapper;

    public PrayerRequestService(JwtService jwtService, PrayerGroupRepository prayerGroupRepository, PrayerGroupUserRepository prayerGroupUserRepository, PrayerRequestRepository prayerRequestRepository, PrayerRequestMapper prayerRequestMapper){
        this.jwtService = jwtService;
        this.prayerGroupRepository = prayerGroupRepository;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
        this.prayerRequestRepository = prayerRequestRepository;
        this.prayerRequestMapper = prayerRequestMapper;
    }

    public PrayerRequestModel createPrayerRequest(String authHeader, int prayerGroupId, PrayerRequestCreateRequest createRequest){
        String token = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(token);

        prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId, userId)
                .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_CREATE));

        PrayerRequestCreateQuery createQuery = new PrayerRequestCreateQuery(
                createRequest.getRequestTitle(),
                createRequest.getRequestDescription(),
                createRequest.getCreatedDate(),
                createRequest.getExpirationDate(),
                prayerGroupId, createRequest.getUserId());

        PrayerRequestCreateResult createResult = prayerRequestRepository.createPrayerRequest(createQuery);
        return prayerRequestMapper.prayerRequestCreateResultToPrayerRequestModel(createResult);
    }

    public PrayerRequestGetResponse getPrayerRequests(String authHeader, PrayerRequestFilterCriteria filterCriteria){
        String token = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(token);

        // TODO: Add Permission Check

        int pageIndex = filterCriteria.getPageIndex();
        int pageSize = filterCriteria.getPageSize();

        PrayerRequestGetQuery getQuery = new PrayerRequestGetQuery(userId, filterCriteria.getPrayerGroupIds(), null, null, filterCriteria.isIncludeExpiredPrayerRequests(), filterCriteria.getSortConfig().getSortField().toString(), filterCriteria.getSortConfig().getSortDirection().toString(), pageIndex * pageSize,  pageSize);
        List<PrayerRequestGetResult> getResults = prayerRequestRepository.getPrayerRequests(getQuery);

        List<PrayerRequestModel> prayerRequests = getResults.stream().map(prayerRequestMapper::prayerRequestGetResultToPrayerRequestModel).toList();
        return new PrayerRequestGetResponse(prayerRequests);
    }
}
