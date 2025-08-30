package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.dtos.CreatePrayerGroupRequestDTO;
import com.kevin.prayerappservice.group.dtos.CreatedPrayerGroupDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupSummaryDTO;
import com.kevin.prayerappservice.group.dtos.PrayerGroupSummaryQueryDTO;
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
        String sql = "SELECT * FROM create_prayer_group(:creatorUserId, :newGroupName, CAST(:groupDescription AS TEXT), CAST(:groupRules AS TEXT), :groupVisibility, :avatarFileId, :bannerFileId)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createPrayerGroupRequest);

        return jdbcTemplate.queryForObject(
                sql,
                params,
                new BeanPropertyRowMapper<>(CreatedPrayerGroupDTO.class)
        );
    }

    public List<PrayerGroupSummaryDTO> getPrayerGroupSummariesByUserId(int userId){
       String sql = "SELECT * FROM get_prayer_group_summaries_by_user(:targetUserId)";
       BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(new PrayerGroupSummaryQueryDTO(userId));

       return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerGroupSummaryDTO.class));
    }
}
