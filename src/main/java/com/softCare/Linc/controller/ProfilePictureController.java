package com.softCare.Linc.controller;

import com.softCare.Linc.model.User;

import com.softCare.Linc.service.LincUserDetailServiceInterface;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Gerald Timmermans <g.h.r.timmermans@st.hanze.nl>
 * <p>
 * controller for uploading profile picture
 */

@Controller
public class ProfilePictureController {

    private final LincUserDetailServiceInterface lincUserDetailServiceInterface;


    public ProfilePictureController(LincUserDetailServiceInterface lincUserDetailServiceInterface) {
        this.lincUserDetailServiceInterface = lincUserDetailServiceInterface;
    }

    @PostMapping("/upload/profilepicture/")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User loggedInUser) throws IOException {
        System.out.println(file);
        loggedInUser.setProfilePicture(file.getBytes());
        lincUserDetailServiceInterface.save(loggedInUser);
        return "redirect:/user/profile";
    }

}
