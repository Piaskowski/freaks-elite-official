package com.freakselite.service.impl;

import com.freakselite.dao.LinksDao;
import com.freakselite.model.Link;
import com.freakselite.service.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MusicServiceImpl implements MusicService {

    // == fields ==
    private final LinksDao linksDao;

    // == constructors ==
    @Autowired
    public MusicServiceImpl(LinksDao linksDao) {
        this.linksDao = linksDao;
    }

    // == public methods ==
    @Override
    public Link getYtVideoLink() {
        return linksDao.findByName("yt-video");
    }
}
