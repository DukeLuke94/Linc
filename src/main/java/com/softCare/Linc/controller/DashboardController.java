package com.softCare.Linc.controller;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final CircleMemberServiceInterface circleMemberServiceInterface;
    private final TaskServiceInterface taskServiceInterface;

    public DashboardController(CircleMemberServiceInterface circleMemberServiceInterface, TaskServiceInterface taskServiceInterface) {
        this.circleMemberServiceInterface = circleMemberServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
    }


    @GetMapping({"/dashboard"})
    protected String showHome(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("allCircles", circleMemberServiceInterface.findAllCirclesWhereMemberOf(user));
        model.addAttribute("tasksPerUser", taskServiceInterface.findAllTasksPerUser(user));
        model.addAttribute("circle",new Circle());
        return "dashboard";
    }
}
