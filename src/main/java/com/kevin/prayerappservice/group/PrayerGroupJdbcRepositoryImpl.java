package com.kevin.prayerappservice.group;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PrayerGroupJdbcRepositoryImpl {
   private final JdbcTemplate jdbcTemplate;

   public PrayerGroupJdbcRepositoryImpl(JdbcTemplate jdbcTemplate){
       this.jdbcTemplate = jdbcTemplate;
   }


}
