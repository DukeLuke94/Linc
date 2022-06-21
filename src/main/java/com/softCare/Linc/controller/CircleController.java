package com.softCare.Linc.controller;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
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
    private final TaskServiceInterface taskServiceInterface;
    public Circle currentCircle;

    public CircleController(CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface) {
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
    }


    @GetMapping({"/home"})
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
            model.addAttribute("tasksToDo",taskServiceInterface.findAllTasksToDoInCircle(currentCircle));
            return "circleDetail";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping({"/circle/new"})
    protected String newCircle(Model model) {
        model.addAttribute("circle", new Circle());
        return "circleForm";
    }

    @PostMapping("/circle/new")
    protected String saveCircle(@ModelAttribute("circle") @Valid Circle circle, BindingResult result) {
        if (!result.hasErrors()) {
            circleServiceInterface.save(circle);
        } else if (result.hasErrors()) {
            return "redirect:/circle/new";
        }
        return "redirect:/home";
    }

    @GetMapping("/circle/delete")
    protected String deleteCircle() {
        circleServiceInterface.delete(currentCircle);
        return "redirect:/home";
    }





}
