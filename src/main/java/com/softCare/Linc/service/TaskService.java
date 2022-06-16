package com.softCare.Linc.service;

import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public Optional<List<Task>> findByCircle(Circle circle){
        return taskRepository.findByCircle(circle);
    }

    public Object findAllTasksToDoInCircle(Circle circle){
        Optional<List<Task>> allTasks = taskRepository.findByCircle(circle);
        List<Task> tasksToDo = new ArrayList<>();
        if (allTasks.isPresent()){
            for (Task allTask : allTasks.get()) {
                if (!allTask.isTaskDone()){
                    tasksToDo.add(allTask);
                }
            }

        }

        return tasksToDo;

    }


}
