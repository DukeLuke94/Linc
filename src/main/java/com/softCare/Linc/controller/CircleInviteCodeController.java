package com.softCare.Linc.controller;

import com.softCare.Linc.model.CircleInviteCode;
import com.softCare.Linc.service.CircleInviteCodeServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    public CircleInviteCodeController(CircleInviteCodeServiceInterface circleInviteCodeServiceInterface, CircleController circleController) {
        this.circleInviteCodeServiceInterface = circleInviteCodeServiceInterface;
        this.circleController = circleController;
    }

    @PostMapping("/invitecode/new")
    protected String getNewCircleInviteCode(@ModelAttribute("circleInviteCode") CircleInviteCode circleInviteCode) {
        getCircleInviteCode(circleInviteCode);
//        boolean circleInviteCodeIsTaken =
//                circleInviteCodeServiceInterface.findByCircleInviteCode(circleInviteCode.getInviteCode()).isPresent();
//        if (circleInviteCodeIsTaken) {
//            circleInviteCode.setInviteCode(circleInviteCodeServiceInterface.generateCode());
//            getNewCircleInviteCode(circleInviteCode);
//        }
        circleInviteCodeServiceInterface.save(circleInviteCode);
        String referrer = circleController.currentCircle.getCircleId().toString();
        return "redirect:/circle/" + referrer;
    }

    private void getCircleInviteCode(CircleInviteCode circleInviteCode) {
        circleInviteCode.setDate(LocalDate.now());
        circleInviteCode.setInviteCode(circleInviteCode.getInviteCode());
        circleInviteCode.setCircle(circleController.currentCircle);
    }
}
