package com.freakselite.dao.daoImpl;

import com.freakselite.dao.BandMemberDao;
import com.freakselite.model.BandMember;
import com.freakselite.rowMappers.BandMemberRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class BandMemberDaoImpl implements BandMemberDao {

    // == fields ==
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final String dbName = "band_member";

    // == constructors ==
    @Autowired
    public BandMemberDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    // == CRUD methods ==
    @Override
    public BandMember findById(int id) {
        SqlParameterSource args = new MapSqlParameterSource("id", id);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE id = :id";

        return jdbcTemplate.queryForObject(query, args, new BandMemberRowMapper());
    }

    @Override
    public List<BandMember> getAll() {
        String query = "SELECT * FROM " + dbName + " ORDER BY arrangement;";
        return jdbcTemplate.query(query, new BandMemberRowMapper());
    }

    @Override
    public boolean insert(BandMember bandMember) {
        SqlParameterSource args = new MapSqlParameterSource("name", bandMember.getName())
                .addValue("surname", bandMember.getSurname())
                .addValue("nick", bandMember.getNick())
                .addValue("description", bandMember.getDescription())
                .addValue("img", bandMember.getImg())
                .addValue("facebook_url", bandMember.getFacebookUrl())
                .addValue("instagram_url", bandMember.getInstagramUrl());

        return simpleJdbcInsert.withTableName(dbName).execute(args) > 0;
    }

    @Override
    public boolean update(BandMember bandMember) {
        SqlParameterSource args = new MapSqlParameterSource("id", bandMember.getId())
                .addValue("name", bandMember.getName())
                .addValue("surname", bandMember.getSurname())
                .addValue("nick", bandMember.getNick())
                .addValue("description", bandMember.getDescription())
                .addValue("img", bandMember.getImg())
                .addValue("facebook_url", bandMember.getFacebookUrl())
                .addValue("instagram_url", bandMember.getInstagramUrl())
                .addValue("arrangement", bandMember.getArrangement());

        String query = "UPDATE " + dbName + " SET " +
                " `name` = :name " +
                ", `surname` = :surname " +
                ", `nick` = :nick " +
                ", `description` = :description " +
                ", `img` = :img " +
                ", `facebook_url` = :facebook_url " +
                ", `instagram_url` = :instagram_url " +
                ", `arrangement` = :arrangement" +
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
