package com.kevin.prayerappservice.file;

import com.kevin.prayerappservice.file.dtos.DeleteFileReference;
import com.kevin.prayerappservice.file.dtos.FileReferencesQuery;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediaFileJdbcRepositoryImpl implements MediaFileJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MediaFileJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DeleteFileReference> getFileReferencesForDelete(int targetFileId){
        FileReferencesQuery query = new FileReferencesQuery(targetFileId);
        String sql = "SELECT * FROM get_file_references_for_delete(:targetFileId);";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(query);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(DeleteFileReference.class));
    }
}
