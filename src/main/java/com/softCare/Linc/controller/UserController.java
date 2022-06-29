package com.softCare.Linc.controller;

import com.softCare.Linc.model.User;
import com.softCare.Linc.model.UserVM;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.UserMapper;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private final String PASSWORD_REPEAT_NO_MATCH = "The entered passwords are not an exact match";

    private final LincUserDetailServiceInterface userInterface;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserController(LincUserDetailServiceInterface userInterface,
                          PasswordEncoder passwordEncoder,
                          UserMapper userMapper) {
        this.userInterface = userInterface;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping({"/login"})
    protected String login(Model model, String error, String logout) {
            if (error != null)
                model.addAttribute("errorMsg", "Your username and password are invalid.");

            if (logout != null)
                model.addAttribute("msg", "You have been logged out successfully.");

            return "login";
    }

    @GetMapping({"/user/new"})
    protected String newUser(Model model) {
        model.addAttribute("userVM", new UserVM());
        return "userForm";
    }

    @PostMapping("/user/new")
    protected String saveOrUpdateUser(@AuthenticationPrincipal User loggedInUser,
                                      @Valid @ModelAttribute("userVM") UserVM userVM, BindingResult result,
                                      Model model) {
        if (!(loggedInUser == null)) {
            User user = userMapper.userVMToUserModel(userVM);
            user.setUserId(loggedInUser.getUserId());
        }
        if (!userVM.getPassword().equals(userVM.getPasswordRepeat())) {
            model.addAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
            return "userForm";
        } else if (!result.hasErrors() && userVM.getPassword().equals(userVM.getPasswordRepeat())) {
            User user = userMapper.userVMToUserModel(userVM);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userInterface.save(user);
            return "redirect:/user/profile";
        }
        return "userForm";
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
