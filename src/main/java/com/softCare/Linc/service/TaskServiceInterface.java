package com.softCare.Linc.service;

import com.softCare.Linc.model.Task;

import java.util.Optional;

/**
 * Project: TaskServiceInterface
 * @author Jan Willem vd Wal on 13-6-2022.
 * Beschrijving:
 */
public interface TaskServiceInterface {
    Optional<Task> findById(Long id);
}
