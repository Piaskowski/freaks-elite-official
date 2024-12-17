package com.freakselite.rowMappers;

import com.freakselite.model.PlannedConcert;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PlannedConcertRowMapper implements RowMapper<PlannedConcert> {
    @Override
    public PlannedConcert mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlannedConcert concert = new PlannedConcert();
        concert.setId(rs.getInt("id"));
        concert.setEventName(rs.getString("event_name"));
        concert.setEventUrl(rs.getString("event_url"));
        concert.setDate(rs.getTimestamp("date").toLocalDateTime());

        concert.setSpot(rs.getString("spot"));
        concert.setStreet(rs.getString("street"));
        concert.setCity(rs.getString("city"));

        return concert;
    }
}
