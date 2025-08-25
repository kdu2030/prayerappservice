package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrayerGroupJdbcRepositoryImpl implements PrayerGroupJdbcRepository {
   private final NamedParameterJdbcTemplate jdbcTemplate;

   public PrayerGroupJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate){
       this.jdbcTemplate = jdbcTemplate;
   }


    public CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest) {
        String sql = "SELECT * FROM createPrayerGroup(:creatorUserId, :newGroupName, :groupDescription, :groupRules, :groupVisibility, :avatarFileId, :bannerFileId)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createPrayerGroupRequest);

        return jdbcTemplate.queryForObject(
                sql,
                params,
                new BeanPropertyRowMapper<>(CreatedPrayerGroupDTO.class)
        );
    }

}
