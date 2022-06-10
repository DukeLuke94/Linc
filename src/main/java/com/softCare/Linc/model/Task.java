package com.softCare.Linc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class Task {

    @Id
    @Column(name = "task_id", nullable = false)
    @GeneratedValue
    private Long taskId;

    private String taskName;

    @ManyToOne
    @JoinColumn(name="circle_id", nullable=false)
    private Circle circle;




}
