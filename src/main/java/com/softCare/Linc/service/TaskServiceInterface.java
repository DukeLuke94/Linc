package com.softCare.Linc.service;

import com.softCare.Linc.model.*;
import com.softCare.Linc.model.DTO.ShortTask;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskServiceInterface {

    Optional<Task> findById(Long id);

    void save(Task task);

    void delete(Task task);

    Optional<List<ShortTask>> findAllShortTasksToDoInCircle(Circle circle);
    Optional<List<Task>> findAllTasksToDoInCircle(Circle circle);

    Object findAllTasksToDoAndToClaimInCircle(Circle circle);

    Object findAllDoneTasksInCircle(Circle circle);

    Object findAllClaimedTasksForUser(User user);

    List<Task> findAllTasksPerUser(User user);

    List<Task> findAllTasksPerUserByCategory(User user, String category);

    Optional<Set<Notification>> dueDateNotificationsPerCircle(List<Circle> circleList);

    Optional<Set<Notification>> dueDateNotificationsPerTask(List<Task> taskList);
}
