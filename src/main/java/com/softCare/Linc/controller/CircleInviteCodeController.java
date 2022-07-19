package com.softCare.Linc.controller;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleInviteCode;
import com.softCare.Linc.service.CircleInviteCodeServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * Project: CircleInviteCodeController
 *
 * @author Jan Willem vd Wal on 12-7-2022.
 * Beschrijving:
 */

@Controller
public class CircleInviteCodeController {

    public final String NOT_EXIST_OR_IS_EXPIRED = "Invite-code does not exist or is expired";
    private final CircleInviteCodeServiceInterface circleInviteCodeServiceInterface;
    private final CircleController circleController;

    public CircleInviteCode currentInviteCode;

    public CircleInviteCodeController(CircleInviteCodeServiceInterface circleInviteCodeServiceInterface,
                                      CircleController circleController) {
        this.circleInviteCodeServiceInterface = circleInviteCodeServiceInterface;
        this.circleController = circleController;
    }


    @PostMapping("/invitecode/new")
    protected String getNewCircleInviteCode(@ModelAttribute("circleInviteCode") CircleInviteCode circleInviteCode) {
        setCircleInviteCode(circleInviteCode);
        boolean circleInviteCodeIsTaken =
                circleInviteCodeServiceInterface.findByCircleInviteCode(circleInviteCode.getInviteCode()).isPresent();
        if (circleInviteCodeIsTaken) {
            circleInviteCode.setInviteCode(circleInviteCodeServiceInterface.generateCode());
            getNewCircleInviteCode(circleInviteCode);
        }
        currentInviteCode = circleInviteCode;
        circleInviteCodeServiceInterface.save(circleInviteCode);
        String referrer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referrer;
    }

    private void setCircleInviteCode(CircleInviteCode circleInviteCode) {
        circleInviteCode.setDate(LocalDate.now().plusDays(7));
        circleInviteCode.setInviteCode(circleInviteCode.getInviteCode());
        circleInviteCode.setCircle(circleController.currentCircle);
    }

    @PostMapping("/invitecode/delete")
    protected String deleteCircleInviteCode(@ModelAttribute("circleInviteCodeToDelete") Long circleInviteCodeId) {
        CircleInviteCode circleInviteCode = circleInviteCodeServiceInterface.findById(circleInviteCodeId).get();
        circleInviteCodeServiceInterface.delete(circleInviteCode);
        String referrer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referrer;
    }

    @GetMapping("/invitecode/join")
    protected String joinCircleWithInviteCode(@RequestParam(required = false, name = "inviteCode") String inviteCode,
                                              @RequestParam(required = false, name = "username") String username,
                                              RedirectAttributes redirectAttributes) {
        if (inviteCode.equals("")) {
            redirectAttributes.addAttribute("username", username);
            return "redirect:/user/new";
        } else if (isCircleInviteCodeValidAndConnectedToCircle(inviteCode)) {
            redirectAttributes.addAttribute("inviteCode", inviteCode);
            redirectAttributes.addAttribute("username", username);
            return "redirect:/user/new";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", NOT_EXIST_OR_IS_EXPIRED);
            redirectAttributes.addAttribute("username", username);
            return "redirect:/";
        }
    }

    private boolean isCircleInviteCodeValidAndConnectedToCircle(String inviteCode) {
        CircleInviteCode circleInviteCode;
        if (circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).isPresent()) {
            circleInviteCode = circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).get();
            return circleInviteCode.getDate().isAfter(LocalDate.now());
        } return false;
    }
}
