package com.kevin.prayerappservice.join;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JoinRequestJdbcRepositoryImpl {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JoinRequestJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


}
