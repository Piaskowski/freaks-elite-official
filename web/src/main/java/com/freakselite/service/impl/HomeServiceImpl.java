package com.freakselite.service.impl;

import com.freakselite.dao.daoImpl.BandMemberDaoImpl;
import com.freakselite.dao.daoImpl.NewsDaoImpl;
import com.freakselite.dao.daoImpl.PlannedConcertDaoImpl;
import com.freakselite.model.BandMember;
import com.freakselite.model.News;
import com.freakselite.model.PlannedConcert;
import com.freakselite.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    // == fields ==
    private final BandMemberDaoImpl bandMemberDaoImpl;
    private final PlannedConcertDaoImpl plannedConcertDaoImpl;
    private final NewsDaoImpl newsDaoImpl;

    // == constructors ==
    @Autowired
    public HomeServiceImpl(BandMemberDaoImpl bandMemberDaoImpl,
                           PlannedConcertDaoImpl plannedConcertDaoImpl,
                           NewsDaoImpl newsDaoImpl){
        this.bandMemberDaoImpl = bandMemberDaoImpl;
        this.plannedConcertDaoImpl = plannedConcertDaoImpl;
        this.newsDaoImpl = newsDaoImpl;
    }

    // == public methods ==
    @Override
    public List<BandMember> getBandMembers() {
        return bandMemberDaoImpl.getAll();
    }

    @Override
    public News getNewest() {
        try {
            return newsDaoImpl.getTop(1).get(0);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public List<PlannedConcert> getConcerts() {
        return plannedConcertDaoImpl.getTop(3);
    }
}
