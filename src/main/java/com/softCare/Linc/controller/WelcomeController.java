package com.softCare.Linc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping({ "/"})
    protected String showWelcome() {

        return "welcome";
    }


}
