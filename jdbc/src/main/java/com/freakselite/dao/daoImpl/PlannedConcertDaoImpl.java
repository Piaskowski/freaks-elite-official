package com.freakselite.dao.daoImpl;

import com.freakselite.dao.PlannedConcertDao;
import com.freakselite.model.PlannedConcert;
import com.freakselite.rowMappers.PlannedConcertRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class PlannedConcertDaoImpl implements PlannedConcertDao {

    // == fields ==
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final String dbName = "concerts";

    // == constructors ==
    @Autowired
    public PlannedConcertDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    // == CRUD methods ==

    @Override
    public PlannedConcert findById(int id) {
        SqlParameterSource args = new MapSqlParameterSource("id", id);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE id = :id";

        return jdbcTemplate.queryForObject(query, args, new PlannedConcertRowMapper());
    }

    @Override
    public List<PlannedConcert> getAll() {
        SqlParameterSource args = new MapSqlParameterSource("date", LocalDateTime.now());
        String query = "SELECT * FROM " +
                dbName +
                " WHERE date >= :date" +
                " ORDER BY date;";

        return jdbcTemplate.query(query, args, new PlannedConcertRowMapper());
    }

    @Override
    public List<PlannedConcert> getTop(int top) {
        SqlParameterSource args = new MapSqlParameterSource("date", LocalDateTime.now());
        String query = "SELECT * FROM "
                + dbName +
                " WHERE date >= :date" +
                " ORDER BY date LIMIT " + top;

        return jdbcTemplate.query(query, args, new PlannedConcertRowMapper());
    }

    @Override
    public boolean insert(PlannedConcert plannedConcert) {
        SqlParameterSource args = new MapSqlParameterSource("event_name", plannedConcert.getEventName())
                .addValue("event_url", plannedConcert.getEventUrl())
                .addValue("date", plannedConcert.getDate())
                .addValue("spot", plannedConcert.getSpot())
                .addValue("street", plannedConcert.getStreet())
                .addValue("city", plannedConcert.getCity());

        return simpleJdbcInsert.withTableName(dbName).execute(args) > 0;
    }

    @Override
    public boolean update(PlannedConcert plannedConcert) {
        SqlParameterSource args = new MapSqlParameterSource("id", plannedConcert.getId())
                .addValue("event_name", plannedConcert.getEventName())
                .addValue("event_url", plannedConcert.getEventUrl())
                .addValue("date", plannedConcert.getDate())
                .addValue("spot", plannedConcert.getSpot())
                .addValue("street", plannedConcert.getStreet())
                .addValue("city", plannedConcert.getCity());


        String query = "UPDATE " + dbName + " SET " +
                " `event_name` = :event_name " +
                ", `event_url` = :event_url " +
                ", `date` = :date " +
                ", `spot` = :spot " +
                ", `street` = :street " +
                ", `city` = :city " +
                " WHERE (`id` = :id );";

        return jdbcTemplate.update(query, args) > 0;
    }

    @Override
    public boolean delete(int id) {
        SqlParameterSource args = new MapSqlParameterSource("id", id);
        String query = "DELETE FROM "
                + dbName
                + " WHERE id = :id";
        return jdbcTemplate.update(query, args) == 1;
    }
}
