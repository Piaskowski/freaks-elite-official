package com.freakselite.dao;

import com.freakselite.model.PlannedConcert;

import java.util.List;

public interface PlannedConcertDao {
    PlannedConcert findById(int id);
    List<PlannedConcert> getAll();
    List<PlannedConcert> getTop(int top);
    boolean insert(PlannedConcert t);
    boolean update(PlannedConcert t);
    boolean delete(int id);
}
