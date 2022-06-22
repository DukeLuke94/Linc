package com.softCare.Linc.service;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;

import java.util.Optional;

public interface TaskServiceInterface {

    Optional<Task> findById(Long id);

    void save(Task task);

    void delete(Task task);

    Object findAllTasksToDoInCircle(Circle circle);

    Object findAllTasksToDoAndToClaimInCircle(Circle circle);

}
