package com.freakselite.controller;

import com.freakselite.model.Link;
import com.freakselite.service.MusicService;
import com.freakselite.util.PageMappings;
import com.freakselite.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MusicController {

    // == fields ==
    private final MusicService musicService;

    // == constructors ==
    @Autowired
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    // == get mappings ==
    @GetMapping(PageMappings.MUSIC)
    public String getMusic(@AuthenticationPrincipal UserDetails userDetails, Model model){
        boolean isAdmin = false;

        if (userDetails != null){
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        Link ytVideoLink = musicService.getYtVideoLink();

        model.addAttribute("ytVideoLink", ytVideoLink);
        model.addAttribute("isAdmin", isAdmin);

        return ViewNames.MUSIC;
    }
}
