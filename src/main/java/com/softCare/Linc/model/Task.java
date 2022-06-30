package com.softCare.Linc.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity @Getter @Setter
public class Task {

    @Id
    @Column(name = "task_id", nullable = false)
    @GeneratedValue
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private boolean taskDone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name="circle_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Circle circle;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    private String claimedUserName;

    public Task(String taskName, String taskDescription, boolean taskDone, Circle circle, LocalDate dueDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDone = taskDone;
        this.circle = circle;
        this.dueDate = dueDate;
    }

    public Task() {
    }


}
