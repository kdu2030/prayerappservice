package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrayerGroupJdbcRepositoryImpl implements PrayerGroupJdbcRepository {
   private final JdbcTemplate jdbcTemplate;

   public PrayerGroupJdbcRepositoryImpl(JdbcTemplate jdbcTemplate){
       this.jdbcTemplate = jdbcTemplate;
   }


    public CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest) {
        String sql = "SELECT * FROM createPrayerGroup(:creatorUserId, :newGroupName, :groupDescription, :groupRules, :groupVisibility, :avatarFileId, :bannerFileId)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createPrayerGroupRequest);

        List<CreatedPrayerGroupDTO> results = jdbcTemplate.query(
                sql,
                (PreparedStatementSetter) params,
                new BeanPropertyRowMapper<>(CreatedPrayerGroupDTO.class)
        );

        if (results.isEmpty()) {
            throw new RuntimeException("Failed to create prayer group - no result returned");
        }

        return results.getFirst();
    }

}
