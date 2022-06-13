package com.softCare.Linc.service;

import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Project: TaskService
 * @author Jan Willem vd Wal on 13-6-2022.
 * Beschrijving:
 */

@Service
public class TaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Optional<Task> findById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(taskMapper::taskToViewModel);
    }
}
