package com.softCare.Linc.controller;

import com.softCare.Linc.Repository.CircleRepository;
import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.model.Circle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class CircleController {

    private final CircleRepository circleRepository;
    private final TaskRepository taskRepository;

    public CircleController(CircleRepository circleRepository, TaskRepository taskRepository) {
        this.circleRepository = circleRepository;
        this.taskRepository = taskRepository;
    }


    @GetMapping({"/home", "/"})
    protected String showHome(Model model) {
        model.addAttribute("allCircles", circleRepository.findAll());
        return "circleOverview";
    }

    @GetMapping("/circle/{circleId}")
    protected String showBookDetails(@PathVariable("circleId") Long circleId, Model model) {
        Optional<Circle> circle = circleRepository.findById(circleId);
        if (circle.isPresent()) {
            model.addAttribute("circle", circle.get());
            return "circleDetail";
        } else {
            return "redirect:/";
        }
    }


}
