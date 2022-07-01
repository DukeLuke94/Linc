package com.softCare.Linc.controller;

import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Lucas Blumers
 *
 * Handles circlemember interactions
 */

@Controller
public class CircleMemberController {

    private final CircleMemberServiceInterface circleMemberServiceInterface;
    private final LincUserDetailServiceInterface userInterface;
    private final CircleController circleController;


    public CircleMemberController(CircleMemberServiceInterface circleMemberServiceInterface, LincUserDetailServiceInterface userInterface, CircleController circleController) {
        this.circleMemberServiceInterface = circleMemberServiceInterface;
        this.userInterface = userInterface;
        this.circleController = circleController;
    }

    @PostMapping("/new/member")
    protected String newMember(@Valid @ModelAttribute("newMemberUser") User user, BindingResult result, Model model) {
        boolean userExists = userInterface.findByeEmail(user.getEmailAddress()).isPresent();
        if (!userExists){
            model.addAttribute("unknownEmail",true);
            return "redirect:/circle/" + circleController.currentCircle.getCircleId();
        }

        Optional<User> member = userInterface.findByeEmail(user.getEmailAddress());
        if (member.isPresent()){
            CircleMember circleMember = new CircleMember(member.get(),circleController.currentCircle,false,false  );
            circleMemberServiceInterface.save(circleMember);
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }



}
