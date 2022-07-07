package com.softCare.Linc.controller;

import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.User;
import com.softCare.Linc.model.UserVmGeneral;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

    @PostMapping("/member/new")
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

    @PostMapping("/member/remove")
    protected String newMember(@ModelAttribute("circleMemberId") Long circleMemberId) {
        Optional<User> user = userInterface.findByUserId(circleMemberId);
        if (user.isPresent()){
            Optional<CircleMember> circleMember = circleMemberServiceInterface.findByUserIdAndCircleId(circleMemberId, circleController.currentCircle.getCircleId());
            if (circleMember.isPresent()){
                user.get().removeMember(circleMember.get());
                circleMemberServiceInterface.delete(circleMember.get());
            }
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping({"/assignRole/admin"})
    protected String assignRoleAdmin(@ModelAttribute("circleMemberId") Long circleMemberId) {
        Optional<User> user = userInterface.findByUserId(circleMemberId);
        if (user.isPresent()) {
            Optional<CircleMember> circleMember = circleMemberServiceInterface.findByUserIdAndCircleId(circleMemberId, circleController.currentCircle.getCircleId());
            circleMember.ifPresent(member -> member.setAdmin(true));
            circleMemberServiceInterface.save(circleMember.get());
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @PostMapping({"/assignRole/client"})
    protected String assignRoleClient(@ModelAttribute("circleMemberId") Long circleMemberId) {
        Optional<User> user = userInterface.findByUserId(circleMemberId);
        if (user.isPresent()) {
            Optional<CircleMember> circleMember = circleMemberServiceInterface.findByUserIdAndCircleId(circleMemberId, circleController.currentCircle.getCircleId());
            circleMember.ifPresent(member -> member.setClient(true));
            circleMemberServiceInterface.save(circleMember.get());
        }
        return "redirect:/circle/" + circleController.currentCircle.getCircleId();
    }

    @GetMapping({"/sysAdmin/users"})
    protected String sysAdminDashboard(@AuthenticationPrincipal User user, Model model) {
        if (user.getUsername().equals("sysAdmin")){
            Collection<? extends User> allUsers = userInterface.findAll();
            model.addAttribute("userList",allUsers);
            return "sysAdminDashboard";
        }else {
            return "redirect:/" ;
        }
    }

    @PostMapping({"/sysAdmin/users/delete"})
    protected String sysAdminDeleteUser(@ModelAttribute("userId") Long userId) {
        Optional<User> user = userInterface.findByUserId(userId);
        if (user.isPresent()) {
            userInterface.delete(user.get());
        }
        return "redirect:/sysAdmin/users" ;
    }






}
