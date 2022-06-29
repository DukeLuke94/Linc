package com.softCare.Linc.seeder;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class Seeder {

    private final CircleServiceInterface circleServiceInterface;
    private final TaskServiceInterface taskServiceInterface;
    private final LincUserDetailServiceInterface lincUserDetailServiceInterface;
    private final CircleMemberServiceInterface circleMemberServiceInterface;
    final PasswordEncoder passwordEncoder;
    private List<Object> circles;

    public Seeder(CircleServiceInterface circleServiceInterface, TaskServiceInterface taskServiceInterface, LincUserDetailServiceInterface lincUserDetailServiceInterface, CircleMemberServiceInterface circleMemberServiceInterface, PasswordEncoder passwordEncoder) {
        this.circleServiceInterface = circleServiceInterface;
        this.taskServiceInterface = taskServiceInterface;
        this.lincUserDetailServiceInterface = lincUserDetailServiceInterface;
        this.circleMemberServiceInterface = circleMemberServiceInterface;
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
        // seed user "admin"
        User admin = new User("admin","admin@admin.nl", passwordEncoder.encode("admin"));
        lincUserDetailServiceInterface.save(admin);

        //seed permissions for the admin

        List<Circle> allCircles = new ArrayList<>();
        allCircles.addAll((Collection<? extends Circle>) circleServiceInterface.findAll());
        for (Circle allCircle : allCircles) {
            circleMemberServiceInterface.save(new CircleMember(admin,allCircle,true,true));
        }


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
                circleServiceInterface.findByCircleName("Oom Diederik"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Naar de kapper",
                "Komende woensdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Ramen wassen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Auto APK",
                "Komende donderdagochtend 8 uur",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Schuur opruimen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Grijze container bij de weg zetten",
                "Komende maandagochtend voor 8 uur",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Schutting repareren",
                "-",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Rummikub spelen",
                "Idealiter in het weekend",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Medicijnen ophalen bij de apotheek",
                "Dinsdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Achter en voor huis",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "TV repareren",
                "Zenders zitten niet meer op de goede plek",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Vissen voeren",
                "2 theelepels",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                exampleDate));
        taskServiceInterface.save(new Task(
                "Visvoer bestellen",
                "Voor komend weekend in huis graag",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                exampleDate));
    }

}
