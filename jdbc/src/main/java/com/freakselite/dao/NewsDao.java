package com.freakselite.dao;

import com.freakselite.model.News;

import java.util.List;

public interface NewsDao {
    News findById(int id);
    List<News> getTop(int top);
    List<News> getOffset(int offset, int limit);
    boolean insert(News t);
    boolean update(News t);
    boolean delete(int id);
    int count();
}
