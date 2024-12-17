package com.freakselite.dao;

import java.util.List;

public interface Dao<T> {
    T findById(int id);
    List<T> getAll();
    List<T> getTop(int top);
    List<T> getOffset(int offset, int limit);
    boolean insert(T t);
    boolean update(T t);
    boolean delete(int id);
    int count();
}
