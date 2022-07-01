package com.softCare.Linc.controller;

import com.softCare.Linc.model.User;
import com.softCare.Linc.model.UserVmEditPassword;
import com.softCare.Linc.model.UserVmGeneral;
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

    public final String CURRENT_PASSWORDS_IS_NOT_CORRECT = "The current passwords is not correct";
    private final String PASSWORD_REPEAT_NO_MATCH = "The newly entered passwords are not an exact match or aren't given";

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
    protected String login() {
            return "login";
    }

    @GetMapping({"/user/new"})
    protected String newUser(Model model) {
        model.addAttribute("userVM", new UserVmGeneral());
        return "userForm";
    }

    @PostMapping("/user/new")
    protected String saveOrUpdateUser(@AuthenticationPrincipal User loggedInUser,
                                      @Valid @ModelAttribute("userVM") UserVmGeneral userVmGeneral, BindingResult result,
                                      Model model) {
        if (!(loggedInUser == null)) {
            User user = userMapper.userVMToUserModel(userVmGeneral);
            user.setUserId(loggedInUser.getUserId());
        }
        if (!userVmGeneral.getPassword().equals(userVmGeneral.getPasswordRepeat())) {
            model.addAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
            return "userForm";
        } else if (!result.hasErrors() && userVmGeneral.getPassword().equals(userVmGeneral.getPasswordRepeat())) {
            User user = userMapper.userVMToUserModel(userVmGeneral);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userInterface.save(user);
            return "redirect:/user/profile";
        }
        return "userForm";
    }


    @GetMapping({"/user/edit"})
    protected String editUser(Authentication authentication, Model model) {
        User user = (User) userInterface.loadUserByUsername(authentication.getName());
        UserVmGeneral userVmGeneral = userMapper.userToViewModel(user);
        model.addAttribute("userVM", userVmGeneral);
        return "userForm";
    }

    @GetMapping({"/user/edit/password"})
    protected String editUserPassword(Authentication authentication, Model model) {
        User user = (User) userInterface.loadUserByUsername(authentication.getName());
        UserVmEditPassword userVmEditPassword = userMapper.userToViewModelEditPassword(user);
        model.addAttribute("userVM", userVmEditPassword);
        return "editPasswordForm";
    }


    @PostMapping({"/user/edit/password"})
    protected String editUserPassword(@Valid @ModelAttribute("userVM") UserVmEditPassword userVmEditPassword, BindingResult result,
                                      @AuthenticationPrincipal User loggedInUser, Model model) {
        if (loggedInUserPasswordMatches(userVmEditPassword, loggedInUser)) {
            if (newGivenPasswordsAreEqual(userVmEditPassword)) {
                if (!result.hasErrors()) {
                    User user = setUpdatedUserWithNewPassword(userVmEditPassword, loggedInUser);
                    userInterface.save(user);
                }
                return "redirect:/user/profile";
            }
            model.addAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
            return "editPasswordForm";
        }
        model.addAttribute("errorMessage", CURRENT_PASSWORDS_IS_NOT_CORRECT);
        return "editPasswordForm";
    }


    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String currentUserName(Authentication authentication, Model model) {
        model.addAttribute("user",userInterface.loadUserByUsername(authentication.getName()));
        return "userProfile";
    }

    private User setUpdatedUserWithNewPassword(UserVmEditPassword userVmEditPassword, User loggedInUser) {
        User user = userMapper.userVmEditPasswordToUserModel(userVmEditPassword);
        user.setUserId(loggedInUser.getUserId());
        user.setUsername(loggedInUser.getUsername());
        user.setEmailAddress(loggedInUser.getEmailAddress());
        user.setPhoneNumber(loggedInUser.getPhoneNumber());
        user.setAssignedTasks(loggedInUser.getAssignedTasks());
        user.setPassword(passwordEncoder.encode(userVmEditPassword.getPassword()));
        return user;
    }

    private boolean newGivenPasswordsAreEqual(UserVmEditPassword userVmEditPassword) {
        return userVmEditPassword.getPassword().equals(userVmEditPassword.getPasswordRepeat());
    }

    private boolean loggedInUserPasswordMatches(UserVmEditPassword userVmEditPassword, User loggedInUser) {
        return passwordEncoder.matches(userVmEditPassword.getCurrentPassword(), loggedInUser.getPassword());
    }
}
