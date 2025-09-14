package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.group.constants.PrayerGroupRole;
import com.kevin.prayerappservice.group.dtos.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
public class PrayerGroupJdbcRepositoryImpl implements PrayerGroupJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public PrayerGroupJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }


    public CreatedPrayerGroupDTO createPrayerGroup(CreatePrayerGroupRequestDTO createPrayerGroupRequest) {
        String sql = "SELECT * FROM create_prayer_group(:creatorUserId, :newGroupName, CAST(:groupDescription AS " +
                "TEXT), CAST(:groupRules AS TEXT), :groupVisibility, :avatarFileId, :bannerFileId)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createPrayerGroupRequest);

        return jdbcTemplate.queryForObject(
                sql,
                params,
                new BeanPropertyRowMapper<>(CreatedPrayerGroupDTO.class)
        );
    }

    public List<PrayerGroupSummaryDTO> getPrayerGroupSummariesByUserId(int userId) {
        String sql = "SELECT * FROM get_prayer_group_summaries_by_user(:targetUserId)";
        BeanPropertySqlParameterSource params =
                new BeanPropertySqlParameterSource(new PrayerGroupSummaryQueryDTO(userId));

        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerGroupSummaryDTO.class));
    }

    public PrayerGroupDTO getPrayerGroup(int prayerGroupId, int userId) {
        String sql = "SELECT * FROM get_prayer_group(:targetPrayerGroupId, :targetUserId);";
        PrayerGroupGetQuery getQueryParams = new PrayerGroupGetQuery(prayerGroupId, userId);
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(getQueryParams);

        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PrayerGroupDTO.class));
    }

    public List<PrayerGroupUserDTO> getPrayerGroupUsers(int prayerGroupId, List<PrayerGroupRole> prayerGroupRoles) {
        String sql = "SELECT * FROM get_prayer_group_users(:groupId, :prayerGroupRoles);";
        String[] rawPrayerGroupRoles = prayerGroupRoles.stream().map(Enum::toString).toArray(String[]::new);
        PrayerGroupUserQuery prayerGroupUserQuery = new PrayerGroupUserQuery(prayerGroupId,
                rawPrayerGroupRoles.length > 0 ? rawPrayerGroupRoles : null);

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(prayerGroupUserQuery);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PrayerGroupUserDTO.class));
    }

    public void updatePrayerGroupUsers(PrayerGroupUserUpdateItem[] prayerGroupUserUpdateItems) throws SQLException {
        Connection connection = dataSource.getConnection();
        Array updateItemsArray = connection.createArrayOf("prayer_group_user_update_item", prayerGroupUserUpdateItems);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("updated_users", updateItemsArray);

        String sql = "CALL update_prayer_group_users(:updated_users);";
        jdbcTemplate.update(sql, params);
    }
}
