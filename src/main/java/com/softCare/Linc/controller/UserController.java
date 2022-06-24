package com.softCare.Linc.controller;

import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final LincUserDetailServiceInterface userInterface;
    private final PasswordEncoder passwordEncoder;


    public UserController(LincUserDetailServiceInterface userInterface, PasswordEncoder passwordEncoder) {
        this.userInterface = userInterface;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/user/new"})
    protected String newUser(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/user/new")
    protected String saveOrUpdateUser(@ModelAttribute("user") User user, @AuthenticationPrincipal User loggedInUser, BindingResult result) {
        if (!(loggedInUser == null)) {
            user.setUserId(loggedInUser.getUserId());
        }
        if (!result.hasErrors() && user.getPassword().equals(user.getPasswordRepeat())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userInterface.save(user);
            return "redirect:/user/profile";
        }
        return "redirect:/user/new";
    }


    @GetMapping({"/user/edit"})
    protected String editUser(Authentication authentication, Model model) {
        model.addAttribute("user", userInterface.loadUserByUsername(authentication.getName()));
        return "userForm";
    }

    @GetMapping({"/user/edit/password"})
    protected String editUserPassword(Model model, @AuthenticationPrincipal User loggedInUser) {
        model.addAttribute("user", loggedInUser);
        return "editPasswordForm";
    }


    @PostMapping({"/user/edit/password"})
    protected String editUserPassword(@ModelAttribute("user") User user, @AuthenticationPrincipal User loggedInUser, BindingResult result) {
        if (passwordEncoder.matches(user.getCurrentPassword(), loggedInUser.getPassword())) {
            if (user.getPassword().equals(user.getPasswordRepeat())) {
                if (!result.hasErrors()) {
                    setUpdatedUser(user, loggedInUser);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userInterface.save(user);
                    return "redirect:/user/profile";
                }
                return "redirect:/user/edit/password";
            }
        }
        return "redirect:/user/edit/password";
    }

    private void setUpdatedUser(User user, User loggedInUser) {
        user.setUserId(loggedInUser.getUserId());
        user.setUsername(loggedInUser.getUsername());
        user.setEmailAddress(loggedInUser.getEmailAddress());
        user.setPhoneNumber(loggedInUser.getPhoneNumber());
        user.setAssignedTasks(loggedInUser.getAssignedTasks());
    }


    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String currentUserName(Authentication authentication, Model model) {
        model.addAttribute("user",userInterface.loadUserByUsername(authentication.getName()));
        return "userProfile";
    }




}
