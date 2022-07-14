package com.softCare.Linc.controller;


import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;
import java.util.Optional;

@Controller
public class TaskController {

    private final CircleController circleController;
    private final UserController userController;
    public Task currentTask;

    private final CircleServiceInterface circleServiceInterface;
    private final TaskServiceInterface taskServiceInterface;
    private final LincUserDetailServiceInterface userService;
    private final CircleMemberServiceInterface circleMemberServiceInterface;

    public TaskController(CircleController circleController, UserController userController, CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface, LincUserDetailServiceInterface userService, CircleMemberServiceInterface circleMemberServiceInterface) {
        this.circleController = circleController;
        this.userController = userController;
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
        this.userService = userService;
        this.circleMemberServiceInterface = circleMemberServiceInterface;
    }

    @GetMapping({"/task/new"})
    protected String createNewTask(Model model) {
        model.addAttribute("circleId", circleController.currentCircle);
        model.addAttribute("task", new Task());
        return "taskForm";
    }

    @PostMapping({"/task/new"})
    protected String saveNewTask(@ModelAttribute("task") Task task, BindingResult result,@AuthenticationPrincipal User user) {
        if (!result.hasErrors()) {
            task.setCircle(circleController.currentCircle);
            task.setAuthor(user);
            task.setCircleName(circleController.currentCircle.getCircleName());
            taskServiceInterface.save(task);
        }
        String referer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referer;
    }

    @GetMapping({"/task/edit/{taskId}"})
    protected String editTask(@PathVariable("taskId") Long taskId, Model model) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()) {
            currentTask = task.get();
            model.addAttribute("task", task.get());
            return "editTasks";
        } else {
            return "redirect:/task/" + taskId;
        }
    }

    @PostMapping({"/task/edit"})
    protected String saveEditedTask(@ModelAttribute("task") Task task, BindingResult result) {
        Optional<Task> currentTask = taskServiceInterface.findById(task.getTaskId());
        Circle circleToSet = taskServiceInterface.findById(task.getTaskId()).get().getCircle();
        User userToSet = taskServiceInterface.findById(task.getTaskId()).get().getUser();
        String claimedUserName = null;
        if (!result.hasErrors()) {
            if (task.getClaimedUserName().length()>2){
                claimedUserName = task.getClaimedUserName();
            }
            task.setAuthor(currentTask.get().getAuthor());
            task.setClaimedUserName(claimedUserName);
            task.setCircle(circleToSet);
            task.setCircleName(circleToSet.getCircleName());
            task.setUser(userToSet);
            taskServiceInterface.save(task);
        }
        return "redirect:/task/" + task.getTaskId();
    }

    @GetMapping("/task/{taskId}")
    protected String showTaskDetails(@PathVariable("taskId") Long taskId, Model model, @AuthenticationPrincipal User user) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()) {

            boolean isAuthor =(task.get().getAuthor().getUserId() == user.getUserId());
            currentTask = task.get();
            model.addAttribute("isAuthor", isAuthor);
            model.addAttribute("isAdmin",circleMemberServiceInterface.isAdminOfTask(user,task.get()));
            model.addAttribute("currentUser",user.getUsername());
            model.addAttribute("task", task.get());
            return "taskDetails";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/task/delete")
    protected String deleteTask() {
        String referer = String.valueOf(currentTask.getCircle().getCircleId());
        taskServiceInterface.delete(currentTask);
        return "redirect:/circle/" + referer;
    }

    @PostMapping(path="/taskbutton",params="Done")
    public String action1(@RequestParam(name = "taskId") Long taskId) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()){
            task.get().setCircle(circleController.currentCircle);
            task.get().setCircleName(circleController.currentCircle.getCircleName());
            task.get().setTaskDone(true);
            taskServiceInterface.save(task.get());
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping(path="/taskbutton",params="Edit")
    public String action2(@RequestParam(name = "taskId") Long taskId) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()) {
            currentTask = task.get();
            return "redirect:/task/edit/"+taskId;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value="/taskbutton",params="New")
    public String action3( Model model) {
        model.addAttribute("circleId", circleController.currentCircle);
        model.addAttribute("task", new Task());
        return "taskForm";
    }

    @GetMapping ({"/task/done"})
    protected String markTaskDone() {
        currentTask.setCircle(circleController.currentCircle);
        currentTask.setCircleName(circleController.currentCircle.getCircleName());
        currentTask.setTaskDone(true);
        taskServiceInterface.save(currentTask);
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @GetMapping ({"/task/claim"})
    protected String assignTaskToMe(@AuthenticationPrincipal User user) {
        currentTask.setCircle(circleController.currentCircle);
        currentTask.setCircleName(circleController.currentCircle.getCircleName());
        currentTask.setUser(user);
        taskServiceInterface.save(currentTask);
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping(path="/taskbutton",params="Claim")
    public String action4(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()) {
            currentTask = task.get();
            currentTask.setCircle(circleController.currentCircle);
            currentTask.setCircleName(circleController.currentCircle.getCircleName());
            currentTask.setUser(user);
            currentTask.setClaimedUserName(user.getUsername());
            taskServiceInterface.save(currentTask);
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping({"/task/claim"})
    protected String claimTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
            currentTask = taskServiceInterface.findById(taskId).get();
            currentTask.setUser(user);
            currentTask.setClaimedUserName(user.getUsername());
            currentTask.setCircle(currentTask.getCircle());
            currentTask.setCircleName(currentTask.getCircleName());
            taskServiceInterface.save(currentTask);
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping({"/task/done"})
    protected String doneTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setTaskDone(true);
        taskServiceInterface.save(currentTask);

        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping({"/task/detail/claim"})
    protected String claimDetailTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setUser(user);
        currentTask.setClaimedUserName(user.getUsername());
        currentTask.setCircle(currentTask.getCircle());
        currentTask.setCircleName(currentTask.getCircleName());
        taskServiceInterface.save(currentTask);
        return "redirect:/task/" + taskId;
    }

    @PostMapping({"/task/detail/done"})
    protected String doneDetailTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setTaskDone(true);
        taskServiceInterface.save(currentTask);
        return "redirect:/circle/" + taskId;
    }

    @PostMapping({"/task/detail/unclaimCD"})
    protected String unclaimCircleDetailTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setUser(null);
        currentTask.setClaimedUserName(null);
        currentTask.setCircle(currentTask.getCircle());
        currentTask.setCircleName(currentTask.getCircleName());
        taskServiceInterface.save(currentTask);
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping({"/task/detail/unclaim"})
    protected String unclaimDetailTask(@RequestParam(name = "taskId") Long taskId, @AuthenticationPrincipal User user) {
        currentTask = taskServiceInterface.findById(taskId).get();
        currentTask.setUser(null);
        currentTask.setClaimedUserName(null);
        currentTask.setCircle(currentTask.getCircle());
        currentTask.setCircleName(currentTask.getCircleName());
        taskServiceInterface.save(currentTask);
        return "redirect:/task/" + taskId;
    }

}
