package com.softCare.Linc.service;

import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Notification;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskServiceInterface {


    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CircleMemberServiceInterface circleMemberServiceInterface;
    private final CircleServiceInterface circleServiceInterface;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, CircleMemberServiceInterface circleMemberServiceInterface, CircleServiceInterface circleServiceInterface) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.circleMemberServiceInterface = circleMemberServiceInterface;
        this.circleServiceInterface = circleServiceInterface;
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
        return tasksToDo.stream().sorted((o1, o2) ->o1.getDueDate().compareTo(o2.getDueDate())).collect(Collectors.toList());
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

    public Object findAllTasksPerUser(User user) {
        User currentUser = user;
        List<Task> allTasks = taskRepository.findAll();
        List<Circle> allCircles = circleMemberServiceInterface.findAllCirclesWhereMemberOf(user);

        List<Task> tasksPerUser = new ArrayList<>();

        for (Task task : allTasks) {
            for (Circle circle : allCircles) {
                if(Objects.equals(task.getCircle().getCircleId(), circle.getCircleId()) && !task.isTaskDone()) {
                    tasksPerUser.add(task);
                }
            }
        }
        return tasksPerUser.stream().sorted((o1, o2) ->o1.getDueDate().compareTo(o2.getDueDate())).collect(Collectors.toList());
    }



    @Override
    public Optional<Set<Notification>> dueDateNotificationsPerCircle(List<Circle> circleList) {
        Set<Notification> notificationSet = new HashSet<>();

        //start iterating every circle
        for (Circle circle : circleList) {
            int nrNotifications = 0;
            List<Task> taskList = circle.getTasks();

            // start iteration tasks of that circle
            for (Task task : taskList) {
                LocalDate dueDate = task.getDueDate();
                LocalDate today = LocalDate.now();

                // if task is soon due, and not done yet, add nrNotifications
                long daysLeft = ChronoUnit.DAYS.between(today,dueDate);
                if ((daysLeft<3) && !task.isTaskDone()){
                    nrNotifications++;
                }
            }
            //make 1 notification per circle that has a due task
            Notification notification = new Notification(circle,nrNotifications);
            notificationSet.add(notification);
        }

        return Optional.of(notificationSet);
    }


}
