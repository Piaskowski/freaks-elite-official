package com.freakselite.controller;

import com.freakselite.model.BandMember;
import com.freakselite.model.News;
import com.freakselite.model.PlannedConcert;
import com.freakselite.service.HomeService;
import com.freakselite.util.PageMappings;
import com.freakselite.util.ViewNames;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Setter
@Controller
public class HomeController {

    // == fields ==
    private HomeService homeService;

    // == constructors ==
    @Autowired
    private HomeController(HomeService homeService){
        this.homeService = homeService;
    }

    // == request methods ==
    @GetMapping(PageMappings.HOME)
    public String init(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<BandMember> bandMembers =  homeService.getBandMembers();
        List<PlannedConcert> concerts = homeService.getConcerts();
        News news = homeService.getNewest();

        model.addAttribute("bandMembers", bandMembers);
        model.addAttribute("concerts", concerts);
        model.addAttribute("news", news);

        boolean isAdmin = false;

        if (userDetails != null){
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);

        return ViewNames.HOME;
    }

}
