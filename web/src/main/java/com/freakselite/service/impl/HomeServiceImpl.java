package com.freakselite.service.impl;

import com.freakselite.dao.daoImpl.BandMemberDao;
import com.freakselite.dao.daoImpl.NewsDao;
import com.freakselite.dao.daoImpl.PlannedConcertDao;
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
    private final BandMemberDao bandMemberDao;
    private final PlannedConcertDao plannedConcertDao;
    private final NewsDao newsDao;

    // == constructors ==
    @Autowired
    public HomeServiceImpl(BandMemberDao bandMemberDao,
                           PlannedConcertDao plannedConcertDao,
                           NewsDao newsDao){
        this.bandMemberDao = bandMemberDao;
        this.plannedConcertDao = plannedConcertDao;
        this.newsDao = newsDao;
    }

    // == public methods ==
    @Override
    public List<BandMember> getBandMembers() {
        return bandMemberDao.getAll();
    }

    @Override
    public News getNewest() {
        try {
            return newsDao.getTop(1).get(0);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public List<PlannedConcert> getConcerts() {
        return plannedConcertDao.getTop(3);
    }
}
