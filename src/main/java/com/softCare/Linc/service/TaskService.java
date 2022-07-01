package com.softCare.Linc.service;

import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Object findAllTasksToDoAndToClaimInCircle(Circle circle) {
        Optional<List<Task>> allTasks = taskRepository.findByCircle(circle);
        List<Task> tasksToDo = new ArrayList<>();
        if (allTasks.isPresent()) {
            for (Task allTask : allTasks.get()) {
                if (!allTask.isTaskDone() & allTask.getUser() == null){
                    tasksToDo.add(allTask);
                }
            }
        }
        return tasksToDo;


    }

    @Override
    public Object findAllDoneTasksInCircle(Circle circle) {
        List<Task> allTasks = taskRepository.findByCircle(circle).get();
        List<Task> doneTasks = new ArrayList<>();
        for (Task allTask : allTasks) {
            if (allTask.isTaskDone()){
                doneTasks.add(allTask);
            }
        }
        return doneTasks;
    }

    public Object findAllClaimedTasksForUser(User user) {
        List<Task> allTasks = taskRepository.findAll();
        List<Task> claimedTasks = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getUser() != null) {
                claimedTasks.add(task);
            }
        }
        return claimedTasks;
    }

}
