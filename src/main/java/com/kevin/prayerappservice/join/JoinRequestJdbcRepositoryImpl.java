package com.kevin.prayerappservice.join;

import com.kevin.prayerappservice.join.dtos.JoinRequestDTO;
import com.kevin.prayerappservice.join.dtos.JoinRequestQuery;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JoinRequestJdbcRepositoryImpl implements JoinRequestJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JoinRequestJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<JoinRequestDTO> getJoinRequests(int prayerGroupId){
        String sql = "SELECT * FROM get_join_requests(:targetPrayerGroupId);";
        JoinRequestQuery joinRequestQuery = new JoinRequestQuery(prayerGroupId);
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(joinRequestQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(JoinRequestDTO.class));
    }
}
