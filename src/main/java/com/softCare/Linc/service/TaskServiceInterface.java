package com.softCare.Linc.service;

import com.softCare.Linc.model.Task;

import java.util.Optional;

public interface TaskServiceInterface {

    Object save(Task task);

    Optional<Task> findById(Long id);

}
