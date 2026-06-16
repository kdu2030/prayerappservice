package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.group.PrayerGroupUserRepository;
import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.request.constants.PrayerRequestErrors;
import com.kevin.prayerappservice.request.dtos.*;
import com.kevin.prayerappservice.request.entities.PrayerRequest;
import com.kevin.prayerappservice.request.entities.PrayerRequestBookmark;
import com.kevin.prayerappservice.request.entities.PrayerRequestComment;
import com.kevin.prayerappservice.request.entities.PrayerRequestLike;
import com.kevin.prayerappservice.request.mappers.PrayerRequestMapper;
import com.kevin.prayerappservice.request.models.*;
import com.kevin.prayerappservice.user.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PrayerRequestService {
    private final JwtService jwtService;
    private final PrayerGroupUserRepository prayerGroupUserRepository;
    private final PrayerRequestRepository prayerRequestRepository;
    private final PrayerRequestMapper prayerRequestMapper;
    private final EntityManager entityManager;
    private final PrayerRequestLikeRepository prayerRequestLikeRepository;
    private final PrayerRequestBookmarkRepository prayerRequestBookmarkRepository;
    private final PrayerRequestCommentRepository prayerRequestCommentRepository;

    public PrayerRequestService(JwtService jwtService, PrayerGroupUserRepository prayerGroupUserRepository, PrayerRequestRepository prayerRequestRepository, PrayerRequestMapper prayerRequestMapper, EntityManager entityManager, PrayerRequestLikeRepository prayerRequestLikeRepository, PrayerRequestBookmarkRepository prayerRequestBookmarkRepository, PrayerRequestCommentRepository prayerRequestCommentRepository) {
        this.jwtService = jwtService;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
        this.prayerRequestRepository = prayerRequestRepository;
        this.prayerRequestMapper = prayerRequestMapper;
        this.entityManager = entityManager;
        this.prayerRequestLikeRepository = prayerRequestLikeRepository;
        this.prayerRequestBookmarkRepository = prayerRequestBookmarkRepository;
        this.prayerRequestCommentRepository = prayerRequestCommentRepository;
    }

    public PrayerRequestModel createPrayerRequest(String authHeader, PrayerRequestCreateRequest createRequest) {
        int prayerGroupId = createRequest.getPrayerGroupId();

        String token = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(token);

        prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerGroupId, userId).orElseThrow(() -> new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_CREATE));

        OffsetDateTime createdDate = Optional.ofNullable(createRequest.getCreatedDate()).orElse(OffsetDateTime.now());

        PrayerRequestCreateQuery createQuery = new PrayerRequestCreateQuery(createRequest.getRequestTitle(), createRequest.getRequestDescription(), createdDate, createRequest.getExpirationDate(), prayerGroupId, createRequest.getUserId());

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

            PrayerRequestCountQuery countQuery = new PrayerRequestCountQuery(userId, prayerGroupIds, null, null, filterCriteria.isIncludeExpiredPrayerRequests());
            PrayerRequestCountResult countResult = prayerRequestRepository.getPrayerRequestsCount(countQuery);

            PrayerRequestGetQuery getQuery = new PrayerRequestGetQuery(userId, prayerGroupIds, null, null, filterCriteria.isIncludeExpiredPrayerRequests(), filterCriteria.getSortConfig().getSortField().toString(), filterCriteria.getSortConfig().getSortDirection().toString(), pageIndex * pageSize, pageSize);

            List<PrayerRequestGetResult> getResults = prayerRequestRepository.getPrayerRequests(getQuery);

            List<PrayerRequestModel> prayerRequests = new ArrayList<>(getResults.stream().map(prayerRequestMapper::prayerRequestGetResultToPrayerRequestModel).toList());

            int[] prayerRequestIds = prayerRequests.stream().mapToInt(PrayerRequestModel::getPrayerRequestId).toArray();
            HashMap<Integer, PrayerRequestUserAction> userActionHashMap = getPrayerRequestIdToActionIdsMap(prayerRequestIds, userId);

            for(int i = 0; i < prayerRequests.size(); i++){
                PrayerRequestModel prayerRequest = prayerRequests.get(i);
                if(!userActionHashMap.containsKey(prayerRequest.getPrayerRequestId())){
                    continue;
                }

                PrayerRequestUserAction userAction = userActionHashMap.get(prayerRequest.getPrayerRequestId());
                prayerRequest.setUserCommentIds(userAction.getUserCommentIds());
                prayerRequest.setUserPrayerSessionIds(userAction.getUserPrayerSessionIds());

                prayerRequests.set(i, prayerRequest);
            }


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

    public PrayerRequestLikeModel createPrayerRequestLike(int prayerRequestId, PrayerRequestActionCreateRequest createRequest) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(prayerRequestId).orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST));

        Optional<PrayerRequestLike> existingPrayerRequestLike = prayerRequestLikeRepository.findByPrayerRequest_prayerRequestIdAndUser_userId(prayerRequestId, createRequest.getUserId());

        if (existingPrayerRequestLike.isPresent()) {
            throw new DataValidationException(PrayerRequestErrors.PRAYER_REQUEST_LIKE_EXISTS);
        }

        OffsetDateTime submittedDate = Optional.ofNullable(createRequest.getSubmittedDate()).orElse(OffsetDateTime.now());
        User submittedUser = entityManager.getReference(User.class, createRequest.getUserId());

        PrayerRequestLike prayerRequestLike = new PrayerRequestLike(submittedDate, submittedUser, prayerRequest);
        prayerRequest.setLikeCount(prayerRequest.getLikeCount() + 1);

        prayerRequestLikeRepository.save(prayerRequestLike);
        prayerRequestRepository.save(prayerRequest);

        return prayerRequestMapper.prayerRequestLikeToPrayerRequestLikeModel(prayerRequestLike);
    }

    public void deletePrayerRequestLike(String authHeader, int prayerRequestLikeId) {
        PrayerRequestLike prayerRequestLike = prayerRequestLikeRepository.findById(prayerRequestLikeId).orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST_LIKE));

        String token = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(token);

        if (prayerRequestLike.getUser().getUserId() != userId) {
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_DELETE_LIKE);
        }

        PrayerRequest prayerRequest = prayerRequestLike.getPrayerRequest();
        prayerRequest.setLikeCount(prayerRequest.getLikeCount() - 1);

        prayerRequestLikeRepository.delete(prayerRequestLike);
        prayerRequestRepository.save(prayerRequest);
    }

    public PrayerRequestBookmarkModel createPrayerRequestBookmark(int prayerRequestId, PrayerRequestActionCreateRequest createRequest) {
        Optional<PrayerRequestBookmark> existingBookmark = prayerRequestBookmarkRepository.findByPrayerRequest_prayerRequestIdAndUser_userId(prayerRequestId, createRequest.getUserId());

        if (existingBookmark.isPresent()) {
            throw new DataValidationException(PrayerRequestErrors.BOOKMARK_ALREADY_EXISTS);
        }

        PrayerRequest prayerRequest = entityManager.getReference(PrayerRequest.class, prayerRequestId);
        User user = entityManager.getReference(User.class, createRequest.getUserId());

        PrayerRequestBookmark prayerRequestBookmark = new PrayerRequestBookmark(prayerRequest, user, Optional.ofNullable(createRequest.getSubmittedDate()).orElse(OffsetDateTime.now()));
        prayerRequestBookmarkRepository.save(prayerRequestBookmark);

        return prayerRequestMapper.prayerRequestBookmarkToModel(prayerRequestBookmark);
    }

    public void deletePrayerRequestBookmark(String authHeader, int prayerRequestBookmarkId) {
        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        PrayerRequestBookmark prayerRequestBookmark = prayerRequestBookmarkRepository.findById(prayerRequestBookmarkId).orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_BOOKMARK));

        if (prayerRequestBookmark.getUser().getUserId() != userId) {
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_DELETE_BOOKMARK);
        }

        prayerRequestBookmarkRepository.delete(prayerRequestBookmark);
    }

    public PrayerRequestDetailsModel getPrayerRequest(String authHeader, int prayerRequestId) {
        try {
            String token = jwtService.extractTokenFromAuthHeader(authHeader);
            int userId = jwtService.extractUserId(token);

            PrayerRequestGetResult prayerRequestResult = prayerRequestRepository.getPrayerRequest(prayerRequestId, userId);
            List<PrayerRequestCommentResult> prayerRequestCommentsResult = prayerRequestRepository.getPrayerRequestComments(prayerRequestId);

            List<PrayerRequestUserSessionResult> userSessionResults = prayerRequestRepository.getPrayerRequestUserSessionIds(new PrayerRequestUserActionIdQuery(new int[] { prayerRequestId }, userId));

            PrayerRequestModel prayerRequest = prayerRequestMapper.prayerRequestGetResultToPrayerRequestModel(prayerRequestResult);

            Stream<PrayerRequestCommentResult> commentResultStream = prayerRequestCommentsResult.stream();
            List<PrayerRequestCommentModel> comments = commentResultStream.map(prayerRequestMapper::prayerRequestCommentToModel).toList();

            PrayerRequestDetailsModel prayerRequestDetails = PrayerRequestDetailsModel.prayerRequestModelToDetailsModel(prayerRequest, comments);

            List<Integer> userCommentIds = prayerRequestCommentsResult
                    .stream()
                    .filter(comment -> comment.getUserId() == userId)
                    .map(PrayerRequestCommentResult::getPrayerRequestCommentId)
                    .toList();

            prayerRequestDetails.setUserCommentIds(userCommentIds);

            List<Integer> userPrayerSessionIds = userSessionResults
                    .stream()
                    .map(PrayerRequestUserSessionResult::getPrayerSessionId)
                    .toList();

            prayerRequestDetails.setUserPrayerSessionIds(userPrayerSessionIds);

            return prayerRequestDetails;
        } catch (UncategorizedSQLException exception) {
            Throwable cause = exception.getCause();
            String exceptionMessage = cause != null ? cause.getMessage() : null;

            if(exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST)){
                throw new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST);
            }

            if (exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW)) {
                throw new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_VIEW);
            }

            throw exception;
        }
    }

    public PrayerRequestCommentModel createPrayerRequestComment(String authHeader, int prayerRequestId, PrayerRequestCommentCreateRequest createRequest) {
        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        if(userId != createRequest.getUserId()){
            throw new DataValidationException(PrayerRequestErrors.USER_ID_DOES_NOT_MATCH);
        }

        PrayerRequestCommentCreateParams createParams = new PrayerRequestCommentCreateParams(prayerRequestId, createRequest.getUserId(), createRequest.getComment(), createRequest.getSubmittedDate());
        try {
            PrayerRequestCommentResult createCommentResult = prayerRequestRepository.createPrayerRequestComment(createParams);
            return prayerRequestMapper.prayerRequestCommentToModel(createCommentResult);
        } catch(UncategorizedSQLException exception){
            Throwable cause = exception.getCause();
            String exceptionMessage = cause != null ? cause.getMessage() : null;

            if(exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST)){
                throw new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST);
            }

            if(exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_COMMENT)){
                throw new DataValidationException(PrayerRequestErrors.USER_MUST_BE_JOINED_TO_COMMENT);
            }

            throw exception;
        }
    }

    public PrayerRequestCommentModel updatePrayerRequestComment(String authHeader, int prayerRequestCommentId, PrayerRequestCommentUpdateRequest updateRequest){
        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        PrayerRequestComment comment = prayerRequestCommentRepository.findById(prayerRequestCommentId).orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST_COMMENT));

        if(comment.getUser().getUserId() != userId){
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_UPDATE_COMMENT);
        }

        comment.setComment(updateRequest.getComment());
        prayerRequestCommentRepository.save(comment);

        return prayerRequestMapper.prayerRequestCommentEntityToModel(comment);
    }

    public void deletePrayerRequestComment(String authHeader, int prayerRequestCommentId){
        PrayerRequestComment comment = prayerRequestCommentRepository
                .findById(prayerRequestCommentId)
                .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST_COMMENT));

        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        if(comment.getUser().getUserId() != userId){
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_DELETE_COMMENT);
        }

        prayerRequestRepository.deletePrayerRequestComment(prayerRequestCommentId);
    }

    public PrayerRequestModel updatePrayerRequest(String authHeader, int prayerRequestId, PrayerRequestUpdateRequest updateRequest){
        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        PrayerRequestUpdateQuery updateQuery = new PrayerRequestUpdateQuery(userId, prayerRequestId, updateRequest.getRequestTitle(), updateRequest.getRequestDescription(), updateRequest.getExpirationDate());

        try {
            PrayerRequestGetResult updatedPrayerRequest = prayerRequestRepository.updatePrayerRequest(updateQuery);
            PrayerRequestModel updatedPrayerRequestModel = prayerRequestMapper.prayerRequestGetResultToPrayerRequestModel(updatedPrayerRequest);

            HashMap<Integer, PrayerRequestUserAction> prayerRequestUserActionHashMap = getPrayerRequestIdToActionIdsMap(new int[] { prayerRequestId }, userId);
            PrayerRequestUserAction prayerRequestUserAction = prayerRequestUserActionHashMap.get(prayerRequestId);

            if(prayerRequestUserAction != null){
                updatedPrayerRequestModel.setUserCommentIds(prayerRequestUserAction.getUserCommentIds());
                updatedPrayerRequestModel.setUserPrayerSessionIds(prayerRequestUserAction.getUserPrayerSessionIds());
            }

            return updatedPrayerRequestModel;
        } catch (UncategorizedSQLException exception){
            Throwable cause = exception.getCause();
            String exceptionMessage = cause != null ? cause.getMessage() : null;

            if(exceptionMessage != null && exceptionMessage.contains(PrayerRequestErrors.ONLY_SUBMITTED_CAN_UPDATE_REQUEST)){
                throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_CAN_UPDATE_REQUEST);
            }

            throw exception;
        }
    }

    public void deletePrayerRequest(String authHeader, int prayerRequestId){
        String authToken = jwtService.extractTokenFromAuthHeader(authHeader);
        int userId = jwtService.extractUserId(authToken);

        PrayerRequest prayerRequestToDelete = prayerRequestRepository
            .findById(prayerRequestId)
            .orElseThrow(() -> new DataValidationException(PrayerRequestErrors.CANNOT_FIND_PRAYER_REQUEST));

        if(!canDeletePrayerRequest(userId, prayerRequestToDelete)){
            throw new DataValidationException(PrayerRequestErrors.ONLY_SUBMITTED_OR_ADMIN_CAN_DELETE_REQUEST);
        }

        prayerRequestRepository.deletePrayerRequest(prayerRequestToDelete.getPrayerRequestId());
    }

    private HashMap<Integer, PrayerRequestUserAction> getPrayerRequestIdToActionIdsMap(int[] prayerRequestIds, int userId){
        PrayerRequestUserActionIdQuery actionIdQuery = new PrayerRequestUserActionIdQuery(prayerRequestIds, userId);

        HashMap<Integer, PrayerRequestUserAction> actionsHashMap = new HashMap<>();

        List<PrayerRequestUserCommentResult> userCommentResults = prayerRequestRepository.getPrayerRequestUserCommentIds(actionIdQuery);
        List<PrayerRequestUserSessionResult> userSessionResults = prayerRequestRepository.getPrayerRequestUserSessionIds(actionIdQuery);

        for(PrayerRequestUserCommentResult userCommentResult: userCommentResults){
            int prayerRequestId = userCommentResult.getPrayerRequestId();
            int commentId = userCommentResult.getPrayerRequestCommentId();

            if(actionsHashMap.containsKey(prayerRequestId)){
                PrayerRequestUserAction action = actionsHashMap.get(prayerRequestId);
                List<Integer> commentIds = action.getUserCommentIds();

                commentIds.add(commentId);
                actionsHashMap.put(prayerRequestId, action);
            } else {
                List<Integer> commentIds = new ArrayList<>(List.of(commentId));
                actionsHashMap.put(prayerRequestId, new PrayerRequestUserAction(prayerRequestId, commentIds, new ArrayList<>()));
            }
        }


        for(PrayerRequestUserSessionResult userSessionResult: userSessionResults){
            int prayerRequestId = userSessionResult.getPrayerRequestId();
            int sessionId = userSessionResult.getPrayerSessionId();

            if(actionsHashMap.containsKey(prayerRequestId)){
                PrayerRequestUserAction action = actionsHashMap.get(prayerRequestId);
                List<Integer> sessionIds = action.getUserPrayerSessionIds();

                sessionIds.add(sessionId);
                actionsHashMap.put(prayerRequestId, action);
            } else {
                List<Integer> sessionIds = new ArrayList<>(List.of(sessionId));
                actionsHashMap.put(prayerRequestId, new PrayerRequestUserAction(prayerRequestId, new ArrayList<>(), sessionIds));
            }
        }

        return actionsHashMap;
    }

    private boolean canDeletePrayerRequest(int userId, PrayerRequest prayerRequestToDelete){
        if(prayerRequestToDelete.getUser().getUserId() == userId){
            return true;
        }

        Optional<PrayerGroupUser> prayerGroupUser = prayerGroupUserRepository.findByPrayerGroup_prayerGroupIdAndUser_userId(prayerRequestToDelete.getPrayerRequestId(), userId);
        return prayerGroupUser.filter(groupUser -> groupUser.getPrayerGroupRole() == PrayerGroupRole.ADMIN).isPresent();
    }

}
