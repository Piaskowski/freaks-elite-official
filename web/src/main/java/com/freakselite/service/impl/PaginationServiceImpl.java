package com.freakselite.service.impl;

import com.freakselite.dao.daoImpl.NewsDaoImpl;
import com.freakselite.model.News;
import com.freakselite.service.PaginationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService<News> {

    // == fields ==
    @Setter
    private int limit = 5;
    private final NewsDaoImpl newsDaoImpl;

    // == constructor ==
    @Autowired
    public PaginationServiceImpl(NewsDaoImpl newsDaoImpl){
        this.newsDaoImpl = newsDaoImpl;
    }

    // == public methods ==
    @Override
    public List<News> nextPage(int id) {
        return null;
    }

    @Override
    public List<News> previousPage(int id) {
        return null;
    }

    @Override
    public List<News> getPage(int pageNo, int pageSize) {
        return newsDaoImpl.getOffset(pageNo, pageSize);
    }

    @Override
    public int getPageCount() {
        return 0;
    }
}
