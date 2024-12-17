package com.freakselite.rowMappers;

import com.freakselite.model.BandMember;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BandMemberRowMapper implements RowMapper<BandMember> {

    @Override
    public BandMember mapRow(ResultSet rs, int rowNum) throws SQLException {
        BandMember bandMember = new BandMember();

        bandMember.setId(rs.getInt("id"));
        bandMember.setName(rs.getString("name"));
        bandMember.setSurname(rs.getString("surname"));
        bandMember.setNick(rs.getString("nick"));
        bandMember.setDescription(rs.getString("description"));
        bandMember.setImg(rs.getString("img"));
        bandMember.setFacebookUrl(rs.getString("facebook_url"));
        bandMember.setInstagramUrl(rs.getString("instagram_url"));
        bandMember.setArrangement(rs.getInt("arrangement"));

        return bandMember;
    }
}
