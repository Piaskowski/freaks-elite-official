package com.freakselite.service.impl;

import com.freakselite.dao.daoImpl.PlannedConcertDaoImpl;
import com.freakselite.model.PlannedConcert;
import com.freakselite.service.ConcertsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConcertsServiceImpl implements ConcertsService {

    // == fields ==
    private final PlannedConcertDaoImpl plannedConcertDaoImpl;

    // == constructors ==
    @Autowired
    public ConcertsServiceImpl(PlannedConcertDaoImpl plannedConcertDaoImpl) {
        this.plannedConcertDaoImpl = plannedConcertDaoImpl;
    }

    // == public methods ==
    @Override
    public List<PlannedConcert> getConcerts() {
        return plannedConcertDaoImpl.getAll();
    }

    @Override
    public boolean deleteConcert(int id) {
        return plannedConcertDaoImpl.delete(id);
    }
}
