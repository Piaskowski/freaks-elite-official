package com.freakselite.dao.daoImpl;

import com.freakselite.dao.Dao;
import com.freakselite.model.News;
import com.freakselite.rowMappers.NewsRowMapper;
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
public class NewsDao implements Dao<News> {

    // == fields ==
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private final String dbName = "news";

    // == constructors ==
    @Autowired
    public NewsDao(NamedParameterJdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    // == CRUD methods ==
    @Override
    public News findById(int id) {
        SqlParameterSource args = new MapSqlParameterSource("id", id);
        String query = "SELECT * FROM " +
                dbName +
                " WHERE id = :id";

        News news = jdbcTemplate.queryForObject(query, args, new NewsRowMapper());
        return news;
    }

    @Override
    public List<News> getAll() {
        return null;
    }

    @Override
    public List<News> getTop(int top) {
        String query = "SELECT * FROM "
                + dbName
                + " LIMIT " + top;
        return jdbcTemplate.query(query, new NewsRowMapper());
    }

    @Override
    public List<News> getOffset(int pageNo, int limit) {
        SqlParameterSource args = new MapSqlParameterSource("offset", pageNo * limit)
                .addValue("limit", limit);

        String query = "SELECT * FROM " + dbName + " ORDER BY date DESC LIMIT :limit OFFSET :offset";
        return jdbcTemplate.query(query, args, new NewsRowMapper());
    }

    @Override
    public boolean insert(News news) {
        SqlParameterSource args = new MapSqlParameterSource("title", news.getTitle())
                .addValue("content", news.getContent())
                .addValue("description", news.getDescription())
                .addValue("url", news.getUrl())
                .addValue("img", news.getImg())
                .addValue("date", news.getDate());
        
        return simpleJdbcInsert.withTableName(dbName).execute(args) > 0;
    }

    @Override
    public boolean update(News news) {
        SqlParameterSource args = new MapSqlParameterSource("id", news.getId())
                .addValue("title", news.getTitle())
                .addValue("content", news.getContent())
                .addValue("description", news.getDescription())
                .addValue("url", news.getUrl())
                .addValue("img", news.getImg());


        String query = "UPDATE " + dbName + " SET " +
                " `title` = :title " +
                ", `content` = :content " +
                ", `description` = :description " +
                ", `url` = :url " +
                ", `img` = :img " +
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

    @Override
    public int count() {
        SqlParameterSource args = new MapSqlParameterSource();
        String query = "SELECT COUNT(*) FROM " + dbName;

        try {
            return jdbcTemplate.queryForObject(query, args, Integer.class);
        }catch (NullPointerException e){
            return -1;
        }
    }
}
