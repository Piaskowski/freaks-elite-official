package com.freakselite.service.impl;

import com.freakselite.dao.daoImpl.PlannedConcertDao;
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
    private final PlannedConcertDao plannedConcertDao;

    // == constructors ==
    @Autowired
    public ConcertsServiceImpl(PlannedConcertDao plannedConcertDao) {
        this.plannedConcertDao = plannedConcertDao;
    }

    // == public methods ==
    @Override
    public List<PlannedConcert> getConcerts() {
        return plannedConcertDao.getAll();
    }

    @Override
    public boolean deleteConcert(int id) {
        return plannedConcertDao.delete(id);
    }
}
