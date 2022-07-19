package com.softCare.Linc.service;

import com.softCare.Linc.model.DTO.ShortTask;
import com.softCare.Linc.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    public List<ShortTask> taskToShortTask(List<Task> taskList){
        List<ShortTask> shortTaskList = new ArrayList<>();
        for (Task task : taskList) {
            ShortTask shortTask = new ShortTask();
            shortTask.setTaskId(task.getTaskId());

            String shortName = task.getTaskName().substring(0, Math.min(task.getTaskName().length(), 40));
            if (shortName.length() == 40){
                shortName = shortName + "...";
            }
            shortTask.setTaskName(shortName);

            String shortDescription = task.getTaskDescription().substring(0, Math.min(task.getTaskDescription().length(), 50));
            if (shortDescription.length() == 50){
                shortDescription = shortDescription + "...";
            }

            shortTask.setTaskDescription(shortDescription);
            shortTask.setCircle(task.getCircle());
            shortTask.setDueDate(task.getDueDate());
            shortTask.setDuration(task.getDuration());
            shortTask.setClaimedUserName(task.getClaimedUserName());
            shortTask.setTaskDone(task.isTaskDone());
            shortTask.setUser(task.getUser());
            shortTask.setAuthor(task.getAuthor());
            shortTask.setCategory(task.getCategory());
            shortTask.setCircleName(task.getCircleName());
            shortTaskList.add(shortTask);
        }

        return shortTaskList;

    }


}
