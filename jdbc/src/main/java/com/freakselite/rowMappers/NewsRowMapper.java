package com.freakselite.rowMappers;

import com.freakselite.model.News;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NewsRowMapper implements RowMapper<News> {
    @Override
    public News mapRow(ResultSet rs, int rowNum) throws SQLException {
        News news = new News();

        news.setId(rs.getInt("id"));
        news.setTitle(rs.getString("title"));
        news.setDescription(rs.getString("description"));
        news.setContent(rs.getString("content"));
        news.setUrl(rs.getString("url"));
        news.setImg(rs.getString("img"));
        news.setDate(LocalDate.from(rs.getTimestamp("date").toLocalDateTime()));

        return news;
    }
}
