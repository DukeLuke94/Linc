package com.softCare.Linc.service;

import com.softCare.Linc.model.Task;
import org.springframework.stereotype.Service;

/**
 * Project: TaskMapper
 * @author Jan Willem vd Wal on 13-6-2022.
 * Beschrijving:
 */

@Service
public class TaskMapper {

    public Task taskToViewModel(Task task){
        Task viewmodel = new Task();
        viewmodel.setTaskId(task.getTaskId());
        viewmodel.setTaskName(task.getTaskName());
        viewmodel.setTaskDescription(task.getTaskDescription());
        viewmodel.setCircle(task.getCircle());
        viewmodel.setDueDate(task.getDueDate());
        viewmodel.setDuration(task.getDuration());
        viewmodel.setClaimedUserName(task.getClaimedUserName());
        viewmodel.setTaskDone(task.isTaskDone());
        viewmodel.setUser(task.getUser());
        viewmodel.setAuthor(task.getAuthor());
        viewmodel.setCategory(task.getCategory());
        viewmodel.setCircleName(task.getCircleName());

        return viewmodel;
    }

}
