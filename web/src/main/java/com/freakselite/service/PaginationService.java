package com.freakselite.service;

import java.util.List;

public interface PaginationService<T> {
    List<T> nextPage(int id);
    List<T> previousPage(int id);
    List<T> getPage(int pageNo, int pageSize);
    int getPageCount();
}
