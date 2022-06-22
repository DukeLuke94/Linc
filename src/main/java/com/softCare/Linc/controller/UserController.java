package com.softCare.Linc.controller;

import com.softCare.Linc.model.User;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final LincUserDetailServiceInterface lincUserDetailServiceInterface;
    private final PasswordEncoder passwordEncoder;


    public UserController(LincUserDetailServiceInterface lincUserDetailServiceInterface, PasswordEncoder passwordEncoder) {
        this.lincUserDetailServiceInterface = lincUserDetailServiceInterface;
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
        lincUserDetailServiceInterface.save(user);
        return "redirect:/home";
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String currentUserName(Authentication authentication, Model model) {
        model.addAttribute("user",userInterface.loadUserByUsername(authentication.getName()));
        return "userProfile";
    }




}
