package com.freakselite.service;

import com.freakselite.model.PlannedConcert;

import java.util.List;

public interface ConcertsService {
    // get all band members
    List<PlannedConcert> getConcerts();

    boolean deleteConcert(int id);
}
