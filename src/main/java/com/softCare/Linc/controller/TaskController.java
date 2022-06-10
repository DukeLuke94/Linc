package com.softCare.Linc.controller;

import com.softCare.Linc.service.CircleServiceInterface;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {

    private final CircleServiceInterface circleServiceInterface;


    public TaskController(CircleServiceInterface circleServiceInterface) {
        this.circleServiceInterface = circleServiceInterface;
    }
}
