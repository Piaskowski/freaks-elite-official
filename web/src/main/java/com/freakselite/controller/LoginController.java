package com.freakselite.controller;

import com.freakselite.util.PageMappings;
import com.freakselite.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // == fields ==

    // == constructors ==
    @Autowired
    public LoginController(){

    }

    // == get mappings ==
    @GetMapping(PageMappings.LOGIN)
    public String getLogin(){
        return ViewNames.LOGIN;
    }
}
