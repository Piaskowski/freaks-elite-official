package com.freakselite.controller;

import com.freakselite.model.PlannedConcert;
import com.freakselite.service.ConcertsService;
import com.freakselite.util.PageMappings;
import com.freakselite.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class ConcertsController {

    // == fields ==
    private final ConcertsService concertsService;

    // == constructors ==
    @Autowired
    public ConcertsController(ConcertsService concertsService){
        this.concertsService = concertsService;
    }

    // == public methods ==
    @GetMapping(PageMappings.CONCERTS)
    public String getConcerts(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<PlannedConcert> concerts = concertsService.getConcerts();

        model.addAttribute("concerts", concerts);
        model.addAttribute("deleteObject", new PlannedConcert());

        boolean isAdmin = false;

        if (userDetails != null){
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);

        return ViewNames.CONCERTS;
    }
}
