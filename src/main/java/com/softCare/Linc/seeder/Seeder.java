package com.softCare.Linc.seeder;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class Seeder {

    private final CircleServiceInterface circleServiceInterface;
    private final TaskServiceInterface taskServiceInterface;
    private final LincUserDetailServiceInterface userDetailServiceInterface;
    private List<Object> circles;
    private List<User> users;


    public Seeder(CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface, LincUserDetailServiceInterface userDetailServiceInterface) {
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
        this.userDetailServiceInterface = userDetailServiceInterface;
    }

    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) {
        circles = new ArrayList<>();
        circles.addAll((Collection<?>) circleServiceInterface.findAll());
        if (circles.size() == 0) {
            seedCircles();
            seedTasks();
        }
        users = new ArrayList<>();
        users.addAll((Collection<? extends User>) userDetailServiceInterface.findAll());
        if (users.size() == 0) {
            seedUsers();
        }
    }

    private void seedUsers() {
        userDetailServiceInterface.save(new User("admin", "admin"));
    }

    public void seedCircles() {
            circleServiceInterface.save(new Circle("Oom Diederik"));
            circleServiceInterface.save(new Circle("Tante Geertruida"));
            circleServiceInterface.save(new Circle("Zorgboerderij 't Haantje"));
            circleServiceInterface.save(new Circle("Oma Riekie"));
            circleServiceInterface.save(new Circle("Woongroep Middenmeer"));
    }

    public void seedTasks() {
        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findById(1L).get()));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Niet te kort AUB",
                false,
                circleServiceInterface.findById(1L).get()));
        taskServiceInterface.save(new Task(
                "Naar de kapper",
                "Komende woensdagmiddag",
                false,
                circleServiceInterface.findById(1L).get()));
        taskServiceInterface.save(new Task(
                "Ramen wassen",
                "-",
                false,
                circleServiceInterface.findById(2L).get()));
        taskServiceInterface.save(new Task(
                "Auto APK",
                "Komende donderdagochtend 8 uur",
                false,
                circleServiceInterface.findById(2L).get()));
        taskServiceInterface.save(new Task(
                "Schuur opruimen",
                "-",
                false,
                circleServiceInterface.findById(2L).get()));
        taskServiceInterface.save(new Task(
                "Grijze container bij de weg zetten",
                "Komende maandagochtend voor 8 uur",
                false,
                circleServiceInterface.findById(3L).get()));
        taskServiceInterface.save(new Task(
                "Schutting repareren",
                "-",
                false,
                circleServiceInterface.findById(3L).get()));
        taskServiceInterface.save(new Task(
                "Rummikub spelen",
                "Idealiter in het weekend",
                false,
                circleServiceInterface.findById(3L).get()));
        taskServiceInterface.save(new Task(
                "Medicijnen ophalen bij de apotheek",
                "Dinsdagmiddag",
                false,
                circleServiceInterface.findById(4L).get()));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Achter en voor huis",
                false,
                circleServiceInterface.findById(4L).get()));
        taskServiceInterface.save(new Task(
                "TV repareren",
                "Zenders zitten niet meer op de goede plek",
                false,
                circleServiceInterface.findById(4L).get()));
        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findById(5L).get()));
        taskServiceInterface.save(new Task(
                "Vissen voeren",
                "2 theelepels",
                false,
                circleServiceInterface.findById(5L).get()));
        taskServiceInterface.save(new Task(
                "Visvoer bestellen",
                "Voor komend weekend in huis graag",
                false,
                circleServiceInterface.findById(5L).get()));
    }

}
