package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.request.constants.PrayerRequestErrors;
import com.kevin.prayerappservice.request.dtos.*;
import com.kevin.prayerappservice.request.mappers.PrayerRequestMapper;
import com.kevin.prayerappservice.request.models.PrayerRequestCreateRequest;
import com.kevin.prayerappservice.request.models.PrayerRequestFilterCriteria;
import com.kevin.prayerappservice.request.models.PrayerRequestGetResponse;
import com.kevin.prayerappservice.request.models.PrayerRequestModel;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrayerRequestService {
    private final JwtService jwtService;
    private final PrayerGroupUserRepository prayerGroupUserRepository;
    private final PrayerRequestRepository prayerRequestRepository;
    private final PrayerRequestMapper prayerRequestMapper;

    public PrayerRequestService(JwtService jwtService, PrayerGroupUserRepository prayerGroupUserRepository,
                                PrayerRequestRepository prayerRequestRepository,
                                PrayerRequestMapper prayerRequestMapper) {
        this.jwtService = jwtService;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
        this.prayerRequestRepository = prayerRequestRepository;
        this.prayerRequestMapper = prayerRequestMapper;
    }

    public PrayerRequestModel createPrayerRequest(String authHeader,
                                                  PrayerRequestCreateRequest createRequest) {
        int prayerGroupId = createRequest.getPrayerGroupId();
        
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

    public PrayerRequestGetResponse getPrayerRequests(String authHeader, PrayerRequestFilterCriteria filterCriteria) {
        try {
            String token = jwtService.extractTokenFromAuthHeader(authHeader);
            int userId = jwtService.extractUserId(token);

            int pageIndex = filterCriteria.getPageIndex();
            int pageSize = filterCriteria.getPageSize();

            int[] prayerGroupIds = filterCriteria.getPrayerGroupIds().stream().mapToInt(Integer::valueOf).toArray();

            PrayerRequestCountQuery countQuery = new PrayerRequestCountQuery(userId, prayerGroupIds, null, null,
                    filterCriteria.isIncludeExpiredPrayerRequests());
            PrayerRequestCountResult countResult = prayerRequestRepository.getPrayerRequestsCount(countQuery);

            PrayerRequestGetQuery getQuery = new PrayerRequestGetQuery(userId, prayerGroupIds, null, null,
                    filterCriteria.isIncludeExpiredPrayerRequests(),
                    filterCriteria.getSortConfig().getSortField().toString(),
                    filterCriteria.getSortConfig().getSortDirection().toString(), pageIndex * pageSize, pageSize);

            List<PrayerRequestGetResult> getResults = prayerRequestRepository.getPrayerRequests(getQuery);

            List<PrayerRequestModel> prayerRequests =
                    getResults.stream().map(prayerRequestMapper::prayerRequestGetResultToPrayerRequestModel).toList();

            int prayerRequestsCount = countResult.getPrayerRequestCount();
            int numberOfPages = (int) Math.ceil(prayerRequestsCount / (double) pageSize);

            return new PrayerRequestGetResponse(prayerRequests, prayerRequestsCount, numberOfPages, pageIndex);
        } catch (UncategorizedSQLException exception) {
            Throwable cause = exception.getCause();
            String exceptionMessage = cause != null ? cause.getMessage() : null;

            if (exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW)) {
                throw new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW);
            }
            throw exception;
        }
    }


}
