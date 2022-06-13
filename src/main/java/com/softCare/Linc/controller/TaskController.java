package com.softCare.Linc.controller;

import com.softCare.Linc.model.Task;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class TaskController {

    private final TaskServiceInterface taskServiceInterface;

    public TaskController(TaskServiceInterface taskServiceInterface) {
        this.taskServiceInterface = taskServiceInterface;
    }

    @GetMapping("/task/{taskId}")
    protected String showTaskDetails(@PathVariable("taskId") Long taskId, Model model) {
        Optional<Task> task = taskServiceInterface.findById(taskId);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "taskDetails";
        } else {
            return "redirect:/";
        }
    }
}
