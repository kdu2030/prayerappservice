package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.dtos.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrayerRequestJdbcRepositoryImpl implements PrayerRequestJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PrayerRequestJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PrayerRequestCreateResult createPrayerRequest(PrayerRequestCreateQuery createQuery){
        String sql = "SELECT * FROM create_prayer_request(:requestTitle, :requestDescription, :createdDate, :expirationDate, :prayerGroupId, :userId);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createQuery);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerRequestCreateResult.class));
    }

    @Override
    public List<PrayerRequestGetResult> getPrayerRequests(PrayerRequestGetQuery getQuery){
        String sql = "SELECT * FROM get_prayer_requests(:targetUserId, :prayerGroupIds, :creatorUserIds, :bookmarkedUserId, :includeExpired, :sortField, :sortDirection, :skip, :take);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(getQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerRequestGetResult.class));
    }

    @Override
    public PrayerRequestCountResult getPrayerRequestsCount(PrayerRequestCountQuery countQuery){
        String sql = "SELECT * FROM get_prayer_requests_count(:targetUserId, :prayerGroupIds, :creatorUserIds, :bookmarkedUserId, :includeExpired);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(countQuery);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerRequestCountResult.class));
    }

    @Override
    public PrayerRequestGetResult getPrayerRequest(int prayerRequestId, int userId){
        String sql = "SELECT * FROM get_prayer_request(:prayerRequestId, :userId);";
        SinglePrayerRequestGetQuery getQuery = new SinglePrayerRequestGetQuery(prayerRequestId, userId);
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(getQuery);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerRequestGetResult.class));
    }

    @Override
    public List<PrayerRequestCommentResult> getPrayerRequestComments(int prayerRequestId){
        String sql = "SELECT * FROM get_prayer_request_comments(:prayerRequestId);";
        PrayerRequestCommentQuery commentQuery = new PrayerRequestCommentQuery(prayerRequestId);
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(commentQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerRequestCommentResult.class));
    }

    @Override
    public PrayerRequestCommentResult createPrayerRequestComment(PrayerRequestCommentCreateParams createParams){
        String sql = "SELECT * FROM add_prayer_request_comment(:prayerRequestId, :userId, :comment, :submittedDate);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createParams);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerRequestCommentResult.class));
    }

    @Override
    public void deletePrayerRequestComment(int prayerRequestCommentId){
        String sql = "CALL delete_prayer_request_comment(:commentId);";
        PrayerRequestCommentDeleteQuery deleteQuery = new PrayerRequestCommentDeleteQuery(prayerRequestCommentId);
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(deleteQuery);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<PrayerRequestUserCommentResult> getPrayerRequestUserCommentIds(PrayerRequestUserActionIdQuery commentQuery){
        String sql = "SELECT * FROM get_user_comment_ids(:prayerRequestIds, :userId);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(commentQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerRequestUserCommentResult.class));
    }

    @Override
    public List<PrayerRequestUserSessionResult> getPrayerRequestUserSessionIds(PrayerRequestUserActionIdQuery sessionQuery){
        String sql = "SELECT * FROM get_user_prayer_session_ids(:prayerRequestIds, :userId);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(sessionQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerRequestUserSessionResult.class));
    }

    @Override
    public PrayerRequestGetResult updatePrayerRequest(PrayerRequestUpdateQuery prayerRequestUpdateQuery){
        String sql = "SELECT * FROM update_prayer_request(:userId, :prayerRequestId, :requestTitle, :requestDescription, :expirationDate);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(prayerRequestUpdateQuery);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerRequestGetResult.class));
    }
}
