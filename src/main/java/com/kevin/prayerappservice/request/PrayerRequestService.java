package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.request.constants.PrayerRequestErrors;
import com.kevin.prayerappservice.request.dtos.*;
import com.kevin.prayerappservice.request.entities.PrayerRequest;
import com.kevin.prayerappservice.request.entities.PrayerRequestBookmark;
import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import com.kevin.prayerappservice.request.mappers.PrayerRequestMapper;
import com.kevin.prayerappservice.request.models.*;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrayerRequestService {
    private final JwtService jwtService;
    private final PrayerGroupUserRepository prayerGroupUserRepository;
    private final PrayerRequestRepository prayerRequestRepository;
    private final PrayerRequestMapper prayerRequestMapper;
    private final EntityManager entityManager;
    private final PrayerRequestLikeRepository prayerRequestLikeRepository;
    private final PrayerRequestBookmarkRepository prayerRequestBookmarkRepository;

    public PrayerRequestService(JwtService jwtService, PrayerGroupUserRepository prayerGroupUserRepository,
                                PrayerRequestRepository prayerRequestRepository,
                                PrayerRequestMapper prayerRequestMapper, EntityManager entityManager, PrayerRequestLikeRepository prayerRequestLikeRepository, PrayerRequestBookmarkRepository prayerRequestBookmarkRepository) {
        this.jwtService = jwtService;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
        this.prayerRequestRepository = prayerRequestRepository;
        this.prayerRequestMapper = prayerRequestMapper;
        this.entityManager = entityManager;
        this.prayerRequestLikeRepository = prayerRequestLikeRepository;
        this.prayerRequestBookmarkRepository = prayerRequestBookmarkRepository;
    }

    public PrayerRequestModel createPrayerRequest(String authHeader,
                                                  PrayerRequestCreateRequest createRequest) {
        int prayerGroupId = createRequest.getPrayerGroupId();
        
        String token = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(token);

        prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId, userId)
                .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_CREATE));

        LocalDateTime createdDate = Optional.ofNullable(createRequest.getCreatedDate()).orElse(LocalDateTime.now());

        PrayerRequestCreateQuery createQuery = new PrayerRequestCreateQuery(
                createRequest.getRequestTitle(),
                createRequest.getRequestDescription(),
                createdDate,
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

    public PrayerRequestLikeModel createPrayerRequestLike(int prayerRequestId, PrayerRequestActionCreateRequest createRequest){
        PrayerRequest prayerRequest = prayerRequestRepository.findById(prayerRequestId)
                .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST));

        Optional<PrayerRequestLike> existingPrayerRequestLike = prayerRequestLikeRepository.findByPrayerRequest_prayerRequestIdAndUser_userId(prayerRequestId, createRequest.getUserId());

        if(existingPrayerRequestLike.isPresent()){
            throw new DataValidationException(PrayerRequestErrors.PRAYER_REQUEST_LIKE_EXISTS);
        }

        LocalDateTime submittedDate = Optional.ofNullable(createRequest.getSubmittedDate()).orElse(LocalDateTime.now());
        User submittedUser = entityManager.getReference(User.class, createRequest.getUserId());

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(submittedDate, submittedUser, prayerRequest);
        prayerRequest.setLikeCount(prayerRequest.getLikeCount() + 1);

        prayerRequestLikeRepository.save(prayerRequestLike);
        prayerRequestRepository.save(prayerRequest);

        return prayerRequestMapper.prayerRequestLikeToPrayerRequestLikeModel(prayerRequestLike);
    }

    public void deletePrayerRequestLike(String authHeader, int prayerRequestLikeId){
        PrayerRequestLike prayerRequestLike = prayerRequestLikeRepository.findById(prayerRequestLikeId)
                .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST_LIKE));

        String token = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(token);

        if(prayerRequestLike.getUser().getUserId() != userId){
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_DELETE_LIKE);
        }

        PrayerRequest prayerRequest = prayerRequestLike.getPrayerRequest();
        prayerRequest.setLikeCount(prayerRequest.getLikeCount() - 1);

        prayerRequestLikeRepository.delete(prayerRequestLike);
        prayerRequestRepository.save(prayerRequest);
    }

    public PrayerRequestBookmarkModel createPrayerRequestBookmark(int prayerRequestId, PrayerRequestActionCreateRequest createRequest){
        Optional<PrayerRequestBookmark> existingBookmark = prayerRequestBookmarkRepository.findByPrayerRequest_prayerRequestIdAndUser_userId(prayerRequestId, createRequest.getUserId());

        if(existingBookmark.isPresent()){
            throw new DataValidationException(PrayerRequestErrors.BOOKMARK_ALREADY_EXISTS);
        }

        PrayerRequest prayerRequest = entityManager.getReference(PrayerRequest.class, prayerRequestId);
        User user = entityManager.getReference(User.class, createRequest.getUserId());

        PrayerRequestBookmark prayerRequestBookmark = new PrayerRequestBookmark(prayerRequest, user, Optional.ofNullable(createRequest.getSubmittedDate()).orElse(LocalDateTime.now()));
        prayerRequestBookmarkRepository.save(prayerRequestBookmark);

        return prayerRequestMapper.prayerRequestBookmarkToModel(prayerRequestBookmark);
    }

    public void deletePrayerRequestBookmark(String authHeader, int prayerRequestBookmarkId){
        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        PrayerRequestBookmark prayerRequestBookmark =
                prayerRequestBookmarkRepository
                .findById(prayerRequestBookmarkId)
                .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_BOOKMARK));

        if(prayerRequestBookmark.getUser().getUserId() != userId){
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_DELETE_BOOKMARK);
        }

        prayerRequestBookmarkRepository.delete(prayerRequestBookmark);
    }

    public PrayerRequestDetailsModel getPrayerRequest(String authHeader, int prayerRequestId){
        try {
            String token = jwtService.extractTokenFromAuthHeader(authHeader);
            int userId = jwtService.extractUserId(token);

            PrayerRequestGetResult prayerRequestResult = prayerRequestRepository.getPrayerRequest(prayerRequestId, userId);
            List<PrayerRequestCommentResult> prayerRequestCommentsResult = prayerRequestRepository.getPrayerRequestComments(prayerRequestId);

            PrayerRequestDetailsModel prayerRequest = (PrayerRequestDetailsModel) prayerRequestMapper.prayerRequestGetResultToPrayerRequestModel(prayerRequestResult);


        } catch(UncategorizedSQLException exception){
            Throwable cause = exception.getCause();
            String exceptionMessage = cause != null ? cause.getMessage() : null;

            if(exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW)){
                throw new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW);
            }

            throw exception;
        }
    }

}
