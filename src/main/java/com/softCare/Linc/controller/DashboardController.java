package com.softCare.Linc.controller;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {

    private final CircleMemberServiceInterface circleMemberServiceInterface;
    private final TaskServiceInterface taskServiceInterface;
    public Task currentTask;


    public DashboardController(CircleMemberServiceInterface circleMemberServiceInterface, TaskServiceInterface taskServiceInterface) {
        this.circleMemberServiceInterface = circleMemberServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
    }


    @GetMapping({"/dashboard"})
    protected String showHome(Model model, @AuthenticationPrincipal User user,@RequestParam(value = "categoryFilter", required = false) String category) {
        model.addAttribute("allCircles", circleMemberServiceInterface.findAllCirclesWhereMemberOf(user));
        if (category == null){
            model.addAttribute("tasksPerUser", taskServiceInterface.findAllTasksPerUser(user));
        }else {
            model.addAttribute("tasksPerUser",taskServiceInterface.findAllTasksPerUserByCategory(user,category));
        }
        model.addAttribute("circle",new Circle());
        model.addAttribute("currentUser", user.getUsername());
        model.addAttribute("notificationList",taskServiceInterface.dueDateNotificationsPerCircle(circleMemberServiceInterface.findAllCirclesWhereMemberOf(user)).get());
        model.addAttribute("taskNotificationList",taskServiceInterface.dueDateNotificationsPerTask( taskServiceInterface.findAllTasksPerUser(user)).get());
        return "dashboard";
    }

    @PostMapping({"/task/claimFD"})
    protected String claimTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setUser(user);
        currentTask.setClaimedUserName(user.getUsername());
        currentTask.setCircle(currentTask.getCircle());
        currentTask.setCircleName(currentTask.getCircle().getCircleName());
        taskServiceInterface.save(currentTask);
        return "redirect:/dashboard/";
    }

    @PostMapping({"/task/doneFD"})
    protected String doneTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setTaskDone(true);
        currentTask.setClaimedUserName(user.getUsername());
        taskServiceInterface.save(currentTask);
        return "redirect:/dashboard/";
    }
}
