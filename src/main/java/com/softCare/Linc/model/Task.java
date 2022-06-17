package com.softCare.Linc.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity @Getter @Setter
public class Task {

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
