package com.softCare.Linc.controller;

import com.softCare.Linc.model.User;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
    protected String saveOrUpdateUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "userForm";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userInterface.save(user);
        return "redirect:/home";
    }


    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String currentUserName(Authentication authentication, Model model) {
        model.addAttribute("user",userInterface.loadUserByUsername(authentication.getName()));
        return "userProfile";
    }




}
