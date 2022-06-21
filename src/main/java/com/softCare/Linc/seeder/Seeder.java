package com.softCare.Linc.seeder;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class Seeder {

    private final CircleServiceInterface circleServiceInterface;
    private final TaskServiceInterface taskServiceInterface;
    private final LincUserDetailServiceInterface lincUserDetailServiceInterface;
    final PasswordEncoder passwordEncoder;
    private List<Object> circles;
    private List<User> users;

    public Seeder(CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface, LincUserDetailServiceInterface lincUserDetailServiceInterface, PasswordEncoder passwordEncoder) {
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
        this.lincUserDetailServiceInterface = lincUserDetailServiceInterface;
        this.passwordEncoder = passwordEncoder;
    }


    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) {
        circles = new ArrayList<>();
        circles.addAll((Collection<?>) circleServiceInterface.findAll());
        if (circles.size() == 0) {
            seedCircles();
            seedTasks();
        }
        if (lincUserDetailServiceInterface.findAll().size() == 0){
            seedUsers();
        }


    }

    private void seedUsers() {
        lincUserDetailServiceInterface.save(new User("admin", passwordEncoder.encode("admin")));
    }

    public void seedCircles() {
            circleServiceInterface.save(new Circle("Oom Diederik"));
            circleServiceInterface.save(new Circle("Tante Geertruida"));
            circleServiceInterface.save(new Circle("Zorgboerderij 't Haantje"));
            circleServiceInterface.save(new Circle("Oma Jantina"));
            circleServiceInterface.save(new Circle("Woongroep Middenmeer"));
    }

    public void seedTasks() {

        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik")));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Niet te kort AUB",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik")));
        taskServiceInterface.save(new Task(
                "Naar de kapper",
                "Komende woensdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik")));
        taskServiceInterface.save(new Task(
                "Ramen wassen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida")));
        taskServiceInterface.save(new Task(
                "Auto APK",
                "Komende donderdagochtend 8 uur",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida")));
        taskServiceInterface.save(new Task(
                "Schuur opruimen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida")));
        taskServiceInterface.save(new Task(
                "Grijze container bij de weg zetten",
                "Komende maandagochtend voor 8 uur",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje")));
        taskServiceInterface.save(new Task(
                "Schutting repareren",
                "-",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje")));
        taskServiceInterface.save(new Task(
                "Rummikub spelen",
                "Idealiter in het weekend",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje")));
        taskServiceInterface.save(new Task(
                "Medicijnen ophalen bij de apotheek",
                "Dinsdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina")));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Achter en voor huis",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina")));
        taskServiceInterface.save(new Task(
                "TV repareren",
                "Zenders zitten niet meer op de goede plek",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina")));
        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer")));
        taskServiceInterface.save(new Task(
                "Vissen voeren",
                "2 theelepels",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer")));
        taskServiceInterface.save(new Task(
                "Visvoer bestellen",
                "Voor komend weekend in huis graag",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer")));
    }

}
