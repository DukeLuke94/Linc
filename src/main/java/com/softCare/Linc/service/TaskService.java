package com.softCare.Linc.service;

import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.model.Task;
import org.springframework.stereotype.Service;

/**
 * @author Gerald Timmermans <g.h.r.timmermans@st.hanze.nl>
 * <p>
 * ZET HIER NEER WAT HET PROGRAMMA DOET!
 */

@Service
public class TaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {

        return taskRepository.save(task);
    }
}
