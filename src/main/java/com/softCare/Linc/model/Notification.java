package com.softCare.Linc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Notification {

    private Circle circle;
    private Task task;
    private Integer numberOfNotifications;

    public Notification(Circle circle, Integer numberOfNotifications) {
        this.circle = circle;
        this.numberOfNotifications = numberOfNotifications;
    }

    public Notification(Task task, Integer numberOfNotifications) {
        this.task = task;
        this.numberOfNotifications = numberOfNotifications;
    }
}
