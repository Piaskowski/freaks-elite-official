package com.freakselite.service;

import com.freakselite.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    List<News> getNews();
    Page<News> findPaginated(Pageable pageable);
    boolean deletePost(int id);
}
