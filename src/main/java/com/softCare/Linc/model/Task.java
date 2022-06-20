package com.softCare.Linc.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity @Getter @Setter
public class Task {

    public Task(String taskName, String taskDescription, boolean taskDone, Circle circle) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDone = taskDone;
        this.circle = circle;
    }

    public Task() {
    }

    @Id
    @Column(name = "task_id", nullable = false)
    @GeneratedValue
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private boolean taskDone;

    @ManyToOne
    @JoinColumn(name="circle_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Circle circle;

}
