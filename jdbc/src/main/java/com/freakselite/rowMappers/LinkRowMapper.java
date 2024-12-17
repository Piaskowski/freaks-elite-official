package com.freakselite.rowMappers;

import com.freakselite.model.Link;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkRowMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {

        Link link = new Link();

        link.setId(rs.getInt("id"));
        link.setName(rs.getString("name"));
        link.setUrl(rs.getString("link"));

        return link;
    }
}
