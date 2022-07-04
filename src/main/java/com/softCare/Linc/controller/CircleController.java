package com.softCare.Linc.controller;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class CircleController {


    private final CircleServiceInterface circleServiceInterface;
    private final TaskServiceInterface taskServiceInterface;
    private final CircleMemberServiceInterface circleMemberInterface;
    public Circle currentCircle;

    public CircleController(CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface, CircleMemberServiceInterface circleMemberInterface, CircleMemberServiceInterface circleMemberInterface1) {
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
        this.circleMemberInterface = circleMemberInterface;
    }

    @GetMapping("/circle/{circleId}")
    protected String showCircleDetails(@PathVariable("circleId") Long circleId, Model model,@AuthenticationPrincipal User user) {
        Optional<Circle> circle = circleServiceInterface.findById(circleId);
        if (circle.isPresent()) {
            if (circleMemberInterface.isMember(user, circle.get())) {
                boolean isAdmin = circleMemberInterface.isAdmin(user,circle.get());
                currentCircle = circle.get();
                model.addAttribute("circle", circle.get());
                model.addAttribute("tasksToDo", taskServiceInterface.findAllTasksToDoInCircle(currentCircle));
                model.addAttribute("doneTasks", taskServiceInterface.findAllDoneTasksInCircle(currentCircle));
                model.addAttribute("circleMembers",circleMemberInterface.findAllMembers(circle.get()));
                model.addAttribute("currentUser",user.getUsername());
                model.addAttribute("isAdmin",isAdmin);
                model.addAttribute("newMemberUser", new User());
                return "circleDetail";
            }
            //TODO: add 'no access' error page
            return "redirect:/dashboard";
        }else return "redirect:/dashboard";

    }

    @GetMapping({"/circle/new"})
    protected String newCircle(Model model) {
        model.addAttribute("circle", new Circle());
        return "circleForm";
    }

    @PostMapping("/circle/new")
    @Transactional
    protected String saveCircle(@ModelAttribute("circle") @Valid Circle circle, BindingResult result,@AuthenticationPrincipal User user) {
        if (!result.hasErrors()) {
            circleServiceInterface.save(circle);
            CircleMember circleMember = new CircleMember(user, circle, true, true);
            circleMemberInterface.save(circleMember);
        } else if (result.hasErrors()) {
            return "redirect:/circle/new";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/circle/delete")
    protected String deleteCircle() {
        circleServiceInterface.delete(currentCircle);
        return "redirect:/dashboard";
    }






}
