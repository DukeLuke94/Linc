package com.softCare.Linc.service;
import com.softCare.Linc.model.Circle;
import org.springframework.stereotype.Service;

@Service
public class CircleMapper {



    public Circle circleToModelView(Circle circle){
        Circle modelview = new Circle();
        modelview.setCircleId(circle.getCircleId());
        modelview.setCircleName(circle.getCircleName());
        modelview.setTasks(circle.getTasks());
        return modelview;
    }
}
