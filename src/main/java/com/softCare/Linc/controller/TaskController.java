package com.softCare.Linc.controller;

import com.softCare.Linc.Repository.TaskRepository;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
