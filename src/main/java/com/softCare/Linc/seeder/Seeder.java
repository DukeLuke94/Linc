package com.softCare.Linc.seeder;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.CircleServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.TaskServiceInterface;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.standard.inline.StandardInlineMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
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
        User admin = new User("sysAdmin",passwordEncoder.encode("admin"),"admin@linc.nl", "0613371337");
        lincUserDetailServiceInterface.save(admin);

        //seed permissions for the admin

        List<Circle> allCircles = new ArrayList<>();
        allCircles.addAll((Collection<? extends Circle>) circleServiceInterface.findAll());
        for (Circle allCircle : allCircles) {
            circleMemberServiceInterface.save(new CircleMember(admin,allCircle,true,true));
        }
    }

    public void seedCircles() {

        Circle oomDiederik = new Circle("Fam. Janssen");
        Circle tanteGeertruida = new Circle("Tante Geertruida's Circle");
        Circle zorgHaantje = new Circle("Zorgboerderij 't Vliegende Schaapje");
        Circle omaJantina = new Circle("Fam. Jonker");
        Circle woongroep = new Circle("Woongroep Middenmeer");
        Circle studentenGroep = new Circle("Thuiszorg Studenten");

        circleServiceInterface.save(oomDiederik);
        circleServiceInterface.save(tanteGeertruida);
        circleServiceInterface.save(zorgHaantje);
        circleServiceInterface.save(omaJantina);
        circleServiceInterface.save(woongroep);
        circleServiceInterface.save(studentenGroep);

        User diederik = new User("Diederik", passwordEncoder.encode("diederik"), "diederik@diederik.nl", "0099009900");
        User hendrik = new User("Hendrik", passwordEncoder.encode("hendrik"), "hendrik@hendrik.nl", "0099009900");
        User geertruida = new User("Geertruida", passwordEncoder.encode("geertruida"), "geertruida@geertruida.nl", "0099009900");
        User jantina = new User("Jantina", passwordEncoder.encode("jantina"), "jantina@jantina.nl", "0099009900");
        User baas = new User("M.Veen", passwordEncoder.encode("mveen"), "mveen@mveen.nl", "0099009900");
        User tess = new User("Tesss", passwordEncoder.encode("tess"), "tess@ziggo.nl", "0653976031");
        // studenten groep
        User Mark = new User("Mark", passwordEncoder.encode("mark"), "mark@mark.nl", "0664852145");
        User Timo = new User("Timo", passwordEncoder.encode("timo"), "timo@timo.nl", "0658963214");
        User Madelein = new User("Madelein", passwordEncoder.encode("madelein"), "madelein@madelein.nl", "0647859631");
        User Roos = new User("Roos", passwordEncoder.encode("roos"), "roos@roos.nl", "0652149325");
        User Henry = new User("Henry", passwordEncoder.encode("henry"), "henry@henry.nl", "0621459874");
        User Thomas = new User("Thomas", passwordEncoder.encode("thomas"), "thomas@thomas.nl", "0635841256");
        User Froukje = new User("Froukje", passwordEncoder.encode("froukje"), "froukje@froukje.nl", "062345420");
        User Betty = new User("Betty", passwordEncoder.encode("betty"), "betty@betty.nl", "066548515");
        User Berend = new User("Berend", passwordEncoder.encode("berend"), "berend@berend.nl", "0365453564");


        lincUserDetailServiceInterface.save(Mark);
        lincUserDetailServiceInterface.save(Timo);
        lincUserDetailServiceInterface.save(Madelein);
        lincUserDetailServiceInterface.save(Roos);
        lincUserDetailServiceInterface.save(Henry);
        lincUserDetailServiceInterface.save(Thomas);
        lincUserDetailServiceInterface.save(Froukje);
        lincUserDetailServiceInterface.save(Betty);
        lincUserDetailServiceInterface.save(Berend);
        lincUserDetailServiceInterface.save(tess);

        CircleMember circleMemberMark = new CircleMember(Mark, studentenGroep, false, true);
        CircleMember circleMemberTimo = new CircleMember(Timo, studentenGroep, false, false);
        CircleMember circleMemberMadelein = new CircleMember(Madelein, studentenGroep, false, false);
        CircleMember circleMemberRoos = new CircleMember(Roos, studentenGroep, false, false);
        CircleMember circleMemberHenry = new CircleMember(Henry, studentenGroep, false, false);
        CircleMember circleMemberThomas = new CircleMember(Thomas, studentenGroep, false, false);
        CircleMember circleMemberFroukje = new CircleMember(Froukje, studentenGroep, false, false);
        CircleMember circleMemberBetty = new CircleMember(Betty, studentenGroep, true, false);
        CircleMember circleMemberBerend = new CircleMember(Berend, studentenGroep, true, false);

        circleMemberServiceInterface.save(circleMemberMark);
        circleMemberServiceInterface.save(circleMemberTimo);
        circleMemberServiceInterface.save(circleMemberMadelein);
        circleMemberServiceInterface.save(circleMemberRoos);
        circleMemberServiceInterface.save(circleMemberHenry);
        circleMemberServiceInterface.save(circleMemberThomas);
        circleMemberServiceInterface.save(circleMemberFroukje);
        circleMemberServiceInterface.save(circleMemberBetty);
        circleMemberServiceInterface.save(circleMemberBerend);


        lincUserDetailServiceInterface.save(diederik);
        lincUserDetailServiceInterface.save(hendrik);
        lincUserDetailServiceInterface.save(geertruida);
        lincUserDetailServiceInterface.save(jantina);
        lincUserDetailServiceInterface.save(baas);


        CircleMember circleMemberDiederik = new CircleMember(diederik, oomDiederik, true, true);
//        CircleMember circleMemberHendrik = new CircleMember(hendrik, oomDiederik, false, false);
        CircleMember circleMemberGeertruida = new CircleMember(geertruida, tanteGeertruida, true, true);
        CircleMember circleMemberJantina = new CircleMember(jantina, omaJantina, true, false);
        CircleMember circleMemberBaas = new CircleMember(baas, woongroep, true, true);
        CircleMember circleMemberTess = new CircleMember(tess, omaJantina, false, true);

        circleMemberServiceInterface.save(circleMemberDiederik);
//        circleMemberServiceInterface.save(circleMemberHendrik);
        circleMemberServiceInterface.save(circleMemberGeertruida);
        circleMemberServiceInterface.save(circleMemberJantina);
        circleMemberServiceInterface.save(circleMemberBaas);
        circleMemberServiceInterface.save(circleMemberTess);


        LocalDate date1 = LocalDate.of(2022, 8, 11);
        LocalDate date2 = LocalDate.of(2022, 8, 7);
        LocalDate date3 = LocalDate.of(2022, 8, 4);
        LocalDate date4 = LocalDate.of(2022, 8, 30);
        LocalDate date5 = LocalDate.of(2022, 8, 16);
        LocalDate date6 = LocalDate.of(2022, 8, 27);
        LocalDate date7 = LocalDate.of(2022, 9, 2);
        LocalDate date8 = LocalDate.of(2022, 9, 14);
        LocalDate date9 = LocalDate.of(2022, 8, 3);
        LocalDate date10 = LocalDate.now().plusDays(2);
        LocalDate date11 = LocalDate.of(2022, 8, 9);

        String outdoor = "Outdoor";
        String indoor = "Indoor";
        String trips = "Transport";
        String games = "Company";


        Task task = new Task("Hulp bij zolder opruimen", "Oude foto albums wel bewaren graag!",
                false, date1, circleServiceInterface.findByCircleName("Fam. Janssen"),
                "Fam. Janssen", hendrik, hendrik.getUsername(), 90, diederik, indoor);
        taskServiceInterface.save(task);
        Circle studentenCircle = circleServiceInterface.findByCircleName("Thuiszorg Studenten");

        taskServiceInterface.save(new Task("Boodschappen doen", "Boter kaas en eieren", false, date10, studentenCircle, "Thuiszorg Studenten", Berend, Madelein.getUsername(), 30, Berend, trips));
        taskServiceInterface.save(new Task("Naar de apotheek", "Ze hebben gisteren gebeld, het recept is aangevuld", false, date1, studentenCircle, "Thuiszorg Studenten", null, null, 15, Timo, trips));
        taskServiceInterface.save(new Task("Muffins bakken", "Betty wil graag iets bakken, ingredienten zijn al in huis", false, date3, studentenCircle, "Thuiszorg Studenten", null, null, 75, Timo, indoor));
        taskServiceInterface.save(new Task("Vogelhuisje ophangen", "Op een rustige plek, niet pal in de zon.", false, date10, studentenCircle, "Thuiszorg Studenten", null, null, 120, Timo, outdoor));
        taskServiceInterface.save(new Task("Berend moet naar de kapper", "Zelf nog even een afspraak maken", false, date6, studentenCircle, "Thuiszorg Studenten", Madelein, Madelein.getUsername(), 30, Timo, trips));
        taskServiceInterface.save(new Task("Gezamelijk Formule 1 kijken", "Go Max!", false, date7, studentenCircle, "Thuiszorg Studenten", Thomas, Thomas.getUsername(), 150, Timo, games));
        taskServiceInterface.save(new Task("Onkruid wieden", "Graag geen bestrijdingsmiddelen gebruiken", false, date8, studentenCircle, "Thuiszorg Studenten", null, null, 20, Timo, outdoor));
        taskServiceInterface.save(new Task("Conifeer snoeien", "De buren begonnen weer te klagen over over te weinig zon", false, date4, studentenCircle, "Thuiszorg Studenten", null, null, 15, Timo, outdoor));
        taskServiceInterface.save(new Task("Gras maaien", "-", false, date3, studentenCircle, "Thuiszorg Studenten", null, null, 15, Timo, outdoor));
        taskServiceInterface.save(new Task("Betty helpen met DigiD aanvragen", "-", true, date8, studentenCircle, "Thuiszorg Studenten", Roos, Roos.getUsername(), 45, Timo, indoor));
        taskServiceInterface.save(new Task("Kopje thee drinken", "-", true, date8, studentenCircle, "Thuiszorg Studenten", Henry, Henry.getUsername(), 15, Timo, games));
        taskServiceInterface.save(new Task("Ramen lappen", "Van binnen én buiten graag", false, date7, studentenCircle, "Thuiszorg Studenten", null, null, 65, Timo, indoor));
        taskServiceInterface.save(new Task("Rummikub spelen", "Berend wel laten winnen!", false, date2, studentenCircle, "Thuiszorg Studenten", null, null, 45, Timo, games));
        taskServiceInterface.save(new Task("Uitje dierentuin", "", true, date2, studentenCircle, "Thuiszorg Studenten", Thomas, Thomas.getUsername(), 240, Timo, trips));


        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Fam. Janssen"),
                "Fam. Janssen",
                date8, 30, diederik, trips));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Niet te kort AUB",
                false,
                circleServiceInterface.findByCircleName("Fam. Janssen"),
                "Fam. Janssen",
                date6, 72, hendrik, outdoor));
        taskServiceInterface.save(new Task(
                "Naar de kapper",
                "Komende woensdagmiddag",
                false,
                circleServiceInterface.findByCircleName("Fam. Janssen"),
                "Fam. Janssen",
                date5, 120, diederik, trips));
        taskServiceInterface.save(new Task(
                "Ramen wassen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida's Circle"),
                "Tante Geertruida",
                date4, 45, geertruida, indoor));
        taskServiceInterface.save(new Task(
                "Auto APK",
                "Komende donderdagochtend 8 uur",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida's Circle"),
                "Tante Geertruida",
                date1, 120, geertruida, trips));
        taskServiceInterface.save(new Task(
                "Schuur opruimen",
                "-",
                false,
                circleServiceInterface.findByCircleName("Tante Geertruida's Circle"),
                "Tante Geertruida",
                date6, 180, geertruida, outdoor));
        taskServiceInterface.save(new Task(
                "Grijze container bij de weg zetten",
                "Komende maandagochtend voor 8 uur",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Vliegende Schaapje"),
                "Zorgboerderij 't Haantje",
                date10, 2, baas, indoor));
        taskServiceInterface.save(new Task(
                "Schutting repareren",
                "-",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Vliegende Schaapje"),
                "Zorgboerderij 't Haantje",
                date8, 540, baas, outdoor));
        taskServiceInterface.save(new Task(
                "Rummikub spelen",
                "Idealiter in het weekend",
                false,
                circleServiceInterface.findByCircleName("Zorgboerderij 't Vliegende Schaapje"),
                "Zorgboerderij 't Haantje",
                date3, 90, baas, games));
        taskServiceInterface.save(new Task(
                "Medicijnen ophalen bij de apotheek",
                "Herhaalmedicatie, ligt klaar na 10:00",
                false,
                circleServiceInterface.findByCircleName("Fam. Jonker"),
                "Fam. Jonker",
                date11, 60, jantina, trips));
        taskServiceInterface.save(new Task(
                "Gras maaien",
                "Achter en voor huis",
                false,
                circleServiceInterface.findByCircleName("Fam. Jonker"),
                "Fam. Jonker",
                date3, 25, jantina, outdoor));
        taskServiceInterface.save(new Task(
                "TV repareren",
                "Zenders zitten niet meer op de goede plek. Jantina wil in ieder geval NPO 1, 2, en 3 weer zo snel mogelijk kijken.",
                false,
                circleServiceInterface.findByCircleName("Fam. Jonker"),
                "Fam. Jonker",
                date9, 5, jantina, indoor));
        taskServiceInterface.save(new Task(
                "Boodschappen doen",
                "Havermout, melk, boter",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                "Woongroep Middenmeer",
                date5, 45, baas, trips));
        taskServiceInterface.save(new Task(
                "Vissen voeren",
                "2 theelepels",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                "Woongroep Middenmeer",
                date5, 2, baas, indoor));
        taskServiceInterface.save(new Task(
                "Visvoer bestellen",
                "Voor komend weekend in huis graag",
                false,
                circleServiceInterface.findByCircleName("Woongroep Middenmeer"),
                "Woongroep Middenmeer",
                date7, 5, baas, indoor));


        // profile images

        File BerendFile = new File("src/main/resources/static/static/images/UserImages/Berend.png");
        byte[] BerendPic = new byte[0];
        try {
            BerendPic = FileUtils.readFileToByteArray(BerendFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Berend.setProfilePicture(BerendPic);
        lincUserDetailServiceInterface.save(Berend);

        File HenryFile = new File("src/main/resources/static/static/images/UserImages/Henry.png");
        byte[] HenryPic = new byte[0];
        try {
            HenryPic = FileUtils.readFileToByteArray(HenryFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Henry.setProfilePicture(HenryPic);
        lincUserDetailServiceInterface.save(Henry);

        File RoosFile = new File("src/main/resources/static/static/images/UserImages/Roos.png");
        byte[] RoosPic = new byte[0];
        try {
            RoosPic = FileUtils.readFileToByteArray(RoosFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Roos.setProfilePicture(RoosPic);
        lincUserDetailServiceInterface.save(Roos);

        File MarkFile = new File("src/main/resources/static/static/images/UserImages/Mark.png");
        byte[] MarkPic = new byte[0];
        try {
            MarkPic = FileUtils.readFileToByteArray(MarkFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Mark.setProfilePicture(MarkPic);
        lincUserDetailServiceInterface.save(Mark);

        File MadeleinFile = new File("src/main/resources/static/static/images/UserImages/Madelein.png");
        byte[] MadeleinPic = new byte[0];
        try {
            MadeleinPic = FileUtils.readFileToByteArray(MadeleinFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Madelein.setProfilePicture(MadeleinPic);
        lincUserDetailServiceInterface.save(Madelein);
    }

}
