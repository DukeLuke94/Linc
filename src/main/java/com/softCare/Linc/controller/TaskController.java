package com.softCare.Linc.controller;


import com.softCare.Linc.model.Task;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
public class TaskController {

    private final CircleController circleController;
    public Task currentTask;

    private final CircleServiceInterface circleServiceInterface;
    private final TaskServiceInterface taskServiceInterface;

    public TaskController(CircleController circleController, CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface) {
        this.circleController = circleController;
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
    }

    @GetMapping({"/task/new"})
    protected String createNewTask(Model model) {
        model.addAttribute("circleId", circleController.currentCircle);
        model.addAttribute("task", new Task());
        return "taskForm";
    }

    @PostMapping({"/task/new"})
    protected String saveNewTask(@ModelAttribute("task") Task task, BindingResult result) {
        if (!result.hasErrors()) {
            task.setCircle(circleController.currentCircle);
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
        if (!result.hasErrors()) {
            task.setCircle(circleController.currentCircle);
            task.setTaskId(currentTask.getTaskId());
            taskServiceInterface.save(task);
        }
        return "redirect:/task/" + task.getTaskId();
    }

    @GetMapping("/task/{taskId}")
    protected String showTaskDetails(@PathVariable("taskId") Long taskId, Model model) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()) {
            currentTask = task.get();
            model.addAttribute("task", task.get());
            return "taskDetails";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/task/delete")
    protected String deleteTask() {
        taskServiceInterface.delete(currentTask);
        String referer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referer;
    }

    @GetMapping ({"/task/done"})
    protected String markTaskDone() {
        currentTask.setCircle(circleController.currentCircle);
        currentTask.setTaskDone(true);
        taskServiceInterface.save(currentTask);
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }


}
