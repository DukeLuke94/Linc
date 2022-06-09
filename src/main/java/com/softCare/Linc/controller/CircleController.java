package com.softCare.Linc.controller;

import com.softCare.Linc.Repository.CircleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CircleController {

    private final CircleRepository circleRepository;


    public CircleController(CircleRepository circleRepository) {
        this.circleRepository = circleRepository;
    }

    @GetMapping({"/home", "/"})
    protected String showHome(Model model) {
        model.addAttribute("allCircles", circleRepository.findAll());
        return "circleOverview";
    }


}
