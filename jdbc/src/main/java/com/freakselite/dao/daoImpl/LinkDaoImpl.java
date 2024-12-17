package com.freakselite.dao.daoImpl;

import com.freakselite.dao.LinksDao;
import com.freakselite.model.Link;
import com.freakselite.rowMappers.LinkRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class LinkDaoImpl implements LinksDao {

    // == fields ==
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String dbName = "links";

    // == constructors ==
    @Autowired
    public LinkDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // == public methods ==
    @Override
    public Link findById(int id) {
        SqlParameterSource args = new MapSqlParameterSource("id", id);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE id = :id";

        return jdbcTemplate.queryForObject(query, args, new LinkRowMapper());
    }

    @Override
    public Link findByName(String name) {
        SqlParameterSource args = new MapSqlParameterSource("name", name);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE name = :name";

        return jdbcTemplate.queryForObject(query, args, new LinkRowMapper());
    }

    @Override
    public boolean update(Link link) {
        SqlParameterSource args = new MapSqlParameterSource("name", link.getName())
                .addValue("link", link.getUrl());

        String query = "UPDATE " + dbName + " SET " +
                " `link` = :link " +
                " WHERE (`name` = :name );";

        return jdbcTemplate.update(query, args) > 0;
    }

}
