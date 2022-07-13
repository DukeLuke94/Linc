package com.softCare.Linc.controller;

import com.softCare.Linc.model.CircleInviteCode;
import com.softCare.Linc.service.CircleInviteCodeServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final CircleInviteCodeServiceInterface circleInviteCodeServiceInterface;
    private final CircleController circleController;

    public CircleInviteCode currentInviteCode;

    public CircleInviteCodeController(CircleInviteCodeServiceInterface circleInviteCodeServiceInterface, CircleController circleController) {
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
    protected String deleteCircleInviteCode() {
        circleInviteCodeServiceInterface.delete(currentInviteCode);
        String referrer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referrer;
    }

    @PostMapping("/invitecode/renew")
    protected String renewCircleInviteCode(RedirectAttributes redirectAttributes) {
//        boolean circleInviteCodeIsPresent =
//                circleInviteCodeServiceInterface.findByCircleInviteCode(currentInviteCode.getInviteCode()).isEmpty();
//        if (circleInviteCodeIsPresent) {
//            circleInviteCodeServiceInterface.delete(currentInviteCode);
//        }
        redirectAttributes.addAttribute("circleInviteCode", new CircleInviteCode(circleInviteCodeServiceInterface.generateCode()));
        String referrer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referrer;
    }





}
