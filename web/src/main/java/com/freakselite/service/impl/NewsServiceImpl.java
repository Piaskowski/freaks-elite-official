package com.freakselite.service.impl;

import com.freakselite.dao.daoImpl.NewsDao;
import com.freakselite.model.News;
import com.freakselite.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    // == fields ==
    private final NewsDao newsDao;

    // == constructors ==
    @Autowired
    public NewsServiceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    // == public methods ==
    @Override
    public List<News> getNews() {
        return newsDao.getTop(2);
    }

    public Page<News> findPaginated(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        List<News> news = newsDao.getOffset(currentPage, pageSize);
        int count = newsDao.count();

        return new PageImpl<>(news, PageRequest.of(currentPage, pageSize), count);
    }

    @Override
    public boolean deletePost(int id) {
        return newsDao.delete(id);
    }
}
