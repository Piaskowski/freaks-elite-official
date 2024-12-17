package com.freakselite.dao;

import com.freakselite.model.Link;

public interface LinksDao {
    Link findById(int id);
    Link findByName(String name);
    boolean update(Link link);
}
