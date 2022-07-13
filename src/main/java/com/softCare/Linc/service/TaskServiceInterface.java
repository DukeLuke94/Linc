package com.softCare.Linc.service;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Notification;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TaskServiceInterface {

    Optional<Task> findById(Long id);

    void save(Task task);

    void delete(Task task);

    Object findAllTasksToDoInCircle(Circle circle);

    Object findAllTasksToDoAndToClaimInCircle(Circle circle);

    Object findAllDoneTasksInCircle(Circle circle);

    Object findAllClaimedTasksForUser(User user);

    Object findAllTasksPerUser(User user);

    Optional<Set<Notification>> dueDateNotificationsPerCircle(List<Circle> circleList);
}
