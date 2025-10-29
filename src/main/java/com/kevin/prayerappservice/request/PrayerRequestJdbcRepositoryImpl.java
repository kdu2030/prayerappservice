package com.kevin.prayerappservice.request;

import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestCreateResult;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetQuery;
import com.kevin.prayerappservice.request.dtos.PrayerRequestGetResult;
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

    public PrayerRequestCreateResult createPrayerRequest(PrayerRequestCreateQuery createQuery){
        String sql = "SELECT * FROM create_prayer_request(:requestTitle, :requestDescription, :createdDate, :expirationDate, :prayerGroupId, :userId);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createQuery);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerRequestCreateResult.class));
    }

    public List<PrayerRequestGetResult> getPrayerRequests(PrayerRequestGetQuery getQuery){
        String sql = "SELECT * FROM get_prayer_requests(:targetUserId, :prayerGroupIds, :creatorUserIds, :bookmarkedUserId, :includeExpired, :sortField, :sortDirection, :skip, :take);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(getQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerRequestGetResult.class));
    }
}
