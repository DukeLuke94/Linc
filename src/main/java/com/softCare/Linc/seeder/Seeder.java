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
import java.util.Optional;

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

        }
        if (lincUserDetailServiceInterface.findByUsername("sysAdmin").isEmpty()) {
            seedUsers();
        }
    }

    private void seedUsers() {
        // seed user "admin"
        User admin = new User("sysAdmin",passwordEncoder.encode("admin"),"a@a.nl", "0909090909");
        lincUserDetailServiceInterface.save(admin);

        //seed permissions for the admin

        List<Circle> allCircles = new ArrayList<>();
        allCircles.addAll((Collection<? extends Circle>) circleServiceInterface.findAll());
        for (Circle allCircle : allCircles) {
            circleMemberServiceInterface.save(new CircleMember(admin,allCircle,true,true));
        }
    }

    public void seedCircles() {

        Circle oomDiederik = new Circle("Oom Diederik");
        Circle tanteGeertruida = new Circle("Tante Geertruida");
        Circle zorgHaantje = new Circle("Zorgboerderij 't Haantje");
        Circle omaJantina = new Circle("Oma Jantina");
        Circle woongroep = new Circle("Woongroep Middenmeer");

        circleServiceInterface.save(oomDiederik);
        circleServiceInterface.save(tanteGeertruida);
        circleServiceInterface.save(zorgHaantje);
        circleServiceInterface.save(omaJantina);
        circleServiceInterface.save(woongroep);

        User diederik = new User("Diederik", passwordEncoder.encode("diederik"),"diederik@diederik.nl", "0099009900");
        User hendrik = new User("Hendrik", passwordEncoder.encode("hendrik"),"hendrik@hendrik.nl", "0099009900");
        User geertruida = new User("Geertruida", passwordEncoder.encode("geertruida"),"geertruida@geertruida.nl", "0099009900");
        User jantina = new User("Jantina", passwordEncoder.encode("jantina"),"jantina@jantina.nl", "0099009900");
        User baas = new User("M.Veen", passwordEncoder.encode("mveen"),"mveen@mveen.nl", "0099009900");

        lincUserDetailServiceInterface.save(diederik);
        lincUserDetailServiceInterface.save(hendrik);
        lincUserDetailServiceInterface.save(geertruida);
        lincUserDetailServiceInterface.save(jantina);
        lincUserDetailServiceInterface.save(baas);

        CircleMember circleMemberDiederik = new CircleMember(diederik,oomDiederik,true,true);
        CircleMember circleMemberHendrik = new CircleMember(hendrik,oomDiederik,false,false);
        CircleMember circleMemberGeertruida = new CircleMember(geertruida,tanteGeertruida,true,true);
        CircleMember circleMemberJantina = new CircleMember(jantina,omaJantina,true,true);
        CircleMember circleMemberBaas = new CircleMember(baas,woongroep,true,true);

        circleMemberServiceInterface.save(circleMemberDiederik);
        circleMemberServiceInterface.save(circleMemberHendrik);
        circleMemberServiceInterface.save(circleMemberGeertruida);
        circleMemberServiceInterface.save(circleMemberJantina);
        circleMemberServiceInterface.save(circleMemberBaas);


        LocalDate date1 = LocalDate.of(2022, 8, 2);
        LocalDate date2 = LocalDate.of(2022, 8, 1);
        LocalDate date3 = LocalDate.of(2022, 8, 4);
        LocalDate date4 = LocalDate.of(2022, 8, 30);
        LocalDate date5 = LocalDate.of(2022, 8, 16);
        LocalDate date6 = LocalDate.of(2022, 8, 27);
        LocalDate date7 = LocalDate.of(2022, 9, 2);
        LocalDate date8 = LocalDate.of(2022, 9, 14);
        LocalDate date9 = LocalDate.of(2022, 9, 5);
        LocalDate date10 = LocalDate.now().plusDays(2);

        String outdoor = "Outdoor";
        String indoor = "Indoor";
        String trips = "Transport";
        String games = "Company";


            Task task = new Task("Hulp bij zolder opruimen","Oude foto albums wel bewaren graag!",
                    false,date1, circleServiceInterface.findByCircleName("Oom Diederik"),
                    "Oom Diederik",hendrik,hendrik.getUsername(),90,diederik,indoor);
            taskServiceInterface.save(task);






        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik"),
                "Oom Diederik",
                date8,30,diederik,trips));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Niet te kort AUB",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik"),
                "Oom Diederik",
                date10,72,hendrik,outdoor));
        taskServiceInterface.save(new Task(
                "Naar de kapper",
                "Komende woensdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Oom Diederik"),
                "Oom Diederik",
                date5,120,diederik,trips));
        taskServiceInterface.save(new Task(
                "Ramen wassen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida"),
                "Tante Geertruida",
                date4,45,geertruida,indoor));
        taskServiceInterface.save(new Task(
                "Auto APK",
                "Komende donderdagochtend 8 uur",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida"),
                "Tante Geertruida",
                date1,120,geertruida,trips));
        taskServiceInterface.save(new Task(
                "Schuur opruimen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida"),
                "Tante Geertruida",
                date6,180,geertruida,outdoor));
        taskServiceInterface.save(new Task(
                "Grijze container bij de weg zetten",
                "Komende maandagochtend voor 8 uur",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje"),
                "Zorgboerderij 't Haantje",
                date10,2,baas,indoor));
        taskServiceInterface.save(new Task(
                "Schutting repareren",
                "-",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje"),
                "Zorgboerderij 't Haantje",
                date8,540,baas,outdoor));
        taskServiceInterface.save(new Task(
                "Rummikub spelen",
                "Idealiter in het weekend",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Haantje"),
                "Zorgboerderij 't Haantje",
                date3,90,baas,games));
        taskServiceInterface.save(new Task(
                "Medicijnen ophalen bij de apotheek",
                "Dinsdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina"),
                "Oma Jantina",
                date2,15,jantina,trips));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Achter en voor huis",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina"),
                "Oma Jantina",
                date3,25,jantina,outdoor));
        taskServiceInterface.save(new Task(
                "TV repareren",
                "Zenders zitten niet meer op de goede plek",
                false,
                circleServiceInterface.findByCircleName("Oma Jantina"),
                "Oma Jantina",
                date10,15,jantina,indoor));
        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                "Woongroep Middenmeer",
                date5,45,baas,trips));
        taskServiceInterface.save(new Task(
                "Vissen voeren",
                "2 theelepels",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                "Woongroep Middenmeer",
                date5,2,baas,indoor));
        taskServiceInterface.save(new Task(
                "Visvoer bestellen",
                "Voor komend weekend in huis graag",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                "Woongroep Middenmeer",
                date7,5,baas,indoor));
    }

}
