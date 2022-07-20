package com.softCare.Linc.service;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.DTO.ShortTask;
import com.softCare.Linc.model.DTO.UserVmGeneral;
import com.softCare.Linc.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    TaskMapper taskMapper = new TaskMapper();

    @Test
    @DisplayName("Task to standard viewModel test")
    void taskToViewModel() {
        Circle testCircle = new Circle();
        LocalDate testDate = LocalDate.now();

        Task task1 = new Task("Task1","Description",false,testCircle,testCircle.getCircleName(), testDate,10);
        Task task2 = new Task("Task2","Description",false,testCircle,testCircle.getCircleName(), testDate,20);


        Task viewModelTask1 = taskMapper.taskToViewModel(task1);
        Task viewModelTask2 = taskMapper.taskToViewModel(task2);

        assertAll(
                () -> assertNotNull(viewModelTask1),
                () -> assertNotNull(viewModelTask2),

                () -> assertEquals(task1.getTaskName(),viewModelTask1.getTaskName()),
                () -> assertEquals(task1.getTaskDescription(),viewModelTask1.getTaskDescription()),
                () -> assertEquals(task1.getCircle(),viewModelTask1.getCircle()),
                () -> assertEquals(task1.getAuthor(),viewModelTask1.getAuthor()),
                () -> assertEquals(task1.getTaskName(),viewModelTask1.getTaskName()),
                () -> assertEquals(task1.getCategory(),viewModelTask1.getCategory()),
                () -> assertEquals(task1.getDuration(),viewModelTask1.getDuration()),


                () -> assertNotEquals(viewModelTask1,viewModelTask2),

                () -> assertNull(viewModelTask1.getAuthor()),
                () -> assertNull(viewModelTask2.getClaimedUserName())

                );
    }

    @Test
    @DisplayName("Shorten task description and name")
    void taskToShortTask() {

        Circle testCircle = new Circle();
        LocalDate testDate = LocalDate.now();
        Task task1 = new Task(
                "This task has a title that is way too long to properly display on the task cards.",
                "This task has a description that is way too long to properly display on the task cards." +
                        "That's why it should be truncated at about 60 or so characters, if that's the case, three dots (...)" +
                        "should be added to the description",false,testCircle,testCircle.getCircleName(), testDate,10);
        Task task2 = new Task(
                "Short Title",
                "Short description",false,testCircle,testCircle.getCircleName(), testDate,10);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        List<ShortTask> shortTaskList = taskMapper.taskToShortTask(taskList);

        ShortTask shortTask1 = shortTaskList.get(0);
        ShortTask shortTask2 = shortTaskList.get(1);

        assertAll(

                () -> assertInstanceOf(ShortTask.class,shortTask1),
                () -> assertInstanceOf(ShortTask.class,shortTask2),

                () -> assertNotEquals(task1.getTaskName(),shortTask1.getCircleName()),
                () -> assertEquals(task2.getTaskName(),shortTask2.getTaskName()),

                () -> assertEquals(TaskMapper.MAX_LENGTH_NAME,shortTask1.getTaskName().length()),
                () -> assertEquals(TaskMapper.MAX_LENGT_DESCRIPTION,shortTask1.getTaskDescription().length()),
                () -> assertEquals(task2.getTaskName().length(),shortTask2.getTaskName().length()),
                () -> assertEquals(task2.getTaskDescription().length(),shortTask2.getTaskDescription().length())

        );


    }
}