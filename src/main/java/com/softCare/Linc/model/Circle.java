package com.softCare.Linc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity @Getter @Setter
public class Circle {


    @Id
    @Column(name = "circle_id", nullable = false)
    @GeneratedValue
    private Long circleId;

    private String circleName;

    @OneToMany (mappedBy = "circle")
    private List<Task> tasks;



}
