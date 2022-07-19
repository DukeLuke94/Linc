package com.softCare.Linc.model.DTO;


import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
/**
 * @author Lucas
 * Beschrijving: Shortens a regular task for UI purposes
 */
@Getter @Setter @AllArgsConstructor
public class ShortTask {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private boolean taskDone;
    private String category;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
    private Circle circle;
    private String circleName;
    private User user;
    private User author;
    private String claimedUserName;
    private int duration;

    public ShortTask() {
    }
}
