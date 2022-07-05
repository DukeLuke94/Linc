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
        boolean userExists = userInterface.findByEmail(user.getEmailAddress()).isPresent();
        Optional<User> toBeMember = userInterface.findByEmail(user.getEmailAddress());
        if (!userExists){
            model.addAttribute("unknownEmail",true);
            return "redirect:/circle/" + circleController.currentCircle.getCircleId();
        }

        boolean noMemberYet = circleMemberServiceInterface.findByUserIdAndCircleId(toBeMember.get().getUserId(), circleController.currentCircle.getCircleId()).isEmpty();

        Optional<User> member = userInterface.findByEmail(user.getEmailAddress());
        if (member.isPresent() && noMemberYet){
            CircleMember circleMember = new CircleMember(member.get(),circleController.currentCircle,false,false  );
            circleMemberServiceInterface.save(circleMember);
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping("/remove/member")
    protected String newMember(@ModelAttribute("userId") Long userId) {
        Optional<User> user = userInterface.findByUserId(userId);
        if (user.isPresent()){
            Optional<CircleMember> circleMember = circleMemberServiceInterface.findByUserIdAndCircleId(userId,circleController.currentCircle.getCircleId());
            if (circleMember.isPresent()){
                user.get().removeMember(circleMember.get());
                circleMemberServiceInterface.delete(circleMember.get());
            }
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }




}
