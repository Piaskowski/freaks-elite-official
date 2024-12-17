package com.freakselite.service;

import com.freakselite.model.BandMember;
import com.freakselite.model.News;
import com.freakselite.model.PlannedConcert;

import java.util.List;

public interface HomeService {

    // get all band members
    List<BandMember> getBandMembers();
    // get the most recent news
    News getNewest();
    // get top planned concerts
    List<PlannedConcert> getConcerts();

}
