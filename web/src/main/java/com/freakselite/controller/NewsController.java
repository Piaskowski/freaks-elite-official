package com.freakselite.controller;

import com.freakselite.dao.UserEntityDao;
import com.freakselite.model.News;
import com.freakselite.service.NewsService;
import com.freakselite.service.impl.PaginationServiceImpl;
import com.freakselite.util.PageMappings;
import com.freakselite.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class NewsController {

    // == fields ==
    private final NewsService newsService;
    private final UserEntityDao userDao;
    private final PaginationServiceImpl pagination;

    // == constructors ==
    @Autowired
    public NewsController(NewsService newsService, UserEntityDao userDao, PaginationServiceImpl pagination) {
        this.newsService = newsService;
        this.userDao = userDao;
        this.pagination = pagination;
        this.pagination.setLimit(3);
    }

    // == get mappings ==
    @GetMapping(PageMappings.NEWS)
    public String getPage(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model,
            @RequestParam Optional<Integer> pageNo,
            @RequestParam Optional<Integer> size){
        int currentPage = pageNo.orElse(1);
        int pageSize = size.orElse(3);

        Page<News> newsPage = newsService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("newsPage", newsPage);
        model.addAttribute("deleteObject", new News());

        int totalPages = newsPage.getTotalPages();
        if (totalPages > 0){
            int startIndex = currentPage <= 2 ? 1 : currentPage - 2;
            int endIndex = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(startIndex, endIndex)  // IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // get user role
        boolean isAdmin = false;

        if (userDetails != null){
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);

        return ViewNames.NEWS;
    }
}
