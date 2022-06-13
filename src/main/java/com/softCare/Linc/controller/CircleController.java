package com.softCare.Linc.controller;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.service.CircleServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class CircleController {


    private final CircleServiceInterface circleServiceInterface;
    public Circle currentCircle;

    public CircleController(CircleServiceInterface circleServiceInterface) {
        this.circleServiceInterface = circleServiceInterface;
    }


    @GetMapping({"/home", "/"})
    protected String showHome(Model model) {
        model.addAttribute("allCircles", circleServiceInterface.findAll());
        return "circleOverview";
    }

    @GetMapping("/circle/{circleId}")
    protected String showCircleDetails(@PathVariable("circleId") Long circleId, Model model) {
        Optional<Circle> circle = circleServiceInterface.findById(circleId);
        if (circle.isPresent()) {
            currentCircle = circle.get();
            model.addAttribute("circle", circle.get());
            return "circleDetail";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping({"/new/circle"})
    protected String newCircle(Model model) {
        model.addAttribute("circle", new Circle());
        return "newCircle";
    }

    @PostMapping("/new/circle")
    protected String saveCircle(@ModelAttribute("circle") @Valid Circle circle, BindingResult result) {
        if (!result.hasErrors()) {
            circleServiceInterface.save(circle);
        } else if (result.hasErrors()) {
            return "redirect:/new/circle";
        }
        return "redirect:/home";
    }




}
