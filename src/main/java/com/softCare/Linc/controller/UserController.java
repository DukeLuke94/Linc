package com.softCare.Linc.controller;

import com.softCare.Linc.model.*;
import com.softCare.Linc.service.CircleInviteCodeServiceInterface;
import com.softCare.Linc.service.CircleMemberServiceInterface;
import com.softCare.Linc.service.LincUserDetailServiceInterface;
import com.softCare.Linc.service.UserMapper;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {
    public final String NO_EMPTY_FIELDS = "All fields have to be filled in";
    public final String EMAIL_ALREADY_IN_USE = "This Email-address is already in use";
    public final String CURRENT_PASSWORD_IS_NOT_CORRECT = "The current password is not correct";
    private final String PASSWORD_REPEAT_NO_MATCH = "The newly entered passwords are not an exact match or aren't given";

    private final LincUserDetailServiceInterface userInterface;
    private final CircleMemberServiceInterface circleMemberServiceInterface;
    private final CircleInviteCodeServiceInterface circleInviteCodeServiceInterface;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserController(LincUserDetailServiceInterface userInterface,
                          CircleMemberServiceInterface circleMemberServiceInterface, CircleInviteCodeServiceInterface circleInviteCodeServiceInterface,
                          PasswordEncoder passwordEncoder,
                          UserMapper userMapper) {
        this.userInterface = userInterface;
        this.circleMemberServiceInterface = circleMemberServiceInterface;
        this.circleInviteCodeServiceInterface = circleInviteCodeServiceInterface;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping({"/login"})
    protected String login() {
            return "login";
    }

    @GetMapping({"/user/new"})
    protected String newUser(@RequestParam(required = false, name = "inviteCode") String inviteCode,
                             @RequestParam(required = false, name = "username") String username,
                             Model model) {
        model.addAttribute("userVM", new UserVmGeneral(username));
        model.addAttribute("inviteCode", inviteCode);
        return "userForm";
    }

    @PostMapping("/user/new")
    protected String saveOrUpdateUser(@RequestParam(required = false, name = "inviteCode") String inviteCode,
                                      @AuthenticationPrincipal User loggedInUser,
                                      @Valid @ModelAttribute("userVM") UserVmGeneral userVmGeneral,
                                      BindingResult result,
                                      Model model) {
        if (thereIsALoggedInUser(loggedInUser)) {
            User user = userMapper.userVMToUserModel(userVmGeneral);
            user.setUserId(loggedInUser.getUserId());
        }
        if (emailAddressIsAlreadyTaken(userVmGeneral) && userVmGeneral.getEmailAddress().contains("@")) {
            model.addAttribute("errorMessage", EMAIL_ALREADY_IN_USE);
            return "userForm";
        }
        if (newPasswordsDoNotMatch(userVmGeneral)) {
            model.addAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
            return "userForm";
        } else if (!result.hasErrors()) {
            User newUser = userMapper.userVMToUserModel(userVmGeneral);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userInterface.save(newUser);
            boolean isInviteCodeConnectedToCircle =
                    circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).isPresent();
            if (isInviteCodeConnectedToCircle) {
                Circle circleToAddNewUserTo = circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).get().getCircle();
                circleMemberServiceInterface.save(new CircleMember(newUser, circleToAddNewUserTo,false,false));
            }
            return "redirect:/dashboard";
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
        if (!model.containsAttribute("userVM")) {
            User user = (User) userInterface.loadUserByUsername(authentication.getName());
            UserVmEditPassword userVmEditPassword = userMapper.userToViewModelEditPassword(user);
            model.addAttribute("userVM", userVmEditPassword);
        }
        return "editPasswordForm";
    }


    @PostMapping({"/user/edit/password"})
    protected String editUserPassword(@Valid @ModelAttribute("userVM") UserVmEditPassword userVmEditPassword, BindingResult result,
                                      @AuthenticationPrincipal User loggedInUser, RedirectAttributes redirectAttributes) {
        if (notAllFieldsAreBlank(userVmEditPassword)) {
            if (loggedInUserPasswordMatches(userVmEditPassword, loggedInUser)) {
                if (newGivenPasswordsAreEqual(userVmEditPassword)) {
                    if (!result.hasErrors()) {
                        User user = setUpdatedUserWithNewPassword(userVmEditPassword, loggedInUser);
                        userInterface.save(user);
                        return "redirect:/user/profile";
                    }
                    return "editPasswordForm";
                }
                redirectAttributes.addFlashAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
                return "redirect:/user/edit/password";
            }
            redirectAttributes.addFlashAttribute("errorMessage", CURRENT_PASSWORD_IS_NOT_CORRECT);
            return "redirect:/user/edit/password";
        }
//        redirectAttributes.addFlashAttribute("errorMessage", NO_EMPTY_FIELDS);
//        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userVmEditPassword", result);
//        redirectAttributes.addFlashAttribute("userVM", userVmEditPassword);
//        return "redirect:/user/edit/password";
        return "editPasswordForm";
    }

    private boolean notAllFieldsAreBlank(UserVmEditPassword userVmEditPassword) {
        return !userVmEditPassword.getCurrentPassword().isBlank() && !userVmEditPassword.getPassword().isBlank() && !userVmEditPassword.getPasswordRepeat().isBlank();
    }


    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String currentUserName(@AuthenticationPrincipal User loggedInUser, Authentication authentication, Model model) {
        model.addAttribute("user",userInterface.loadUserByUsername(authentication.getName()));
        if (loggedInUser.getProfilePicture() != null) {
            model.addAttribute("profilePicture", Base64.getEncoder().encodeToString(loggedInUser.getProfilePicture()));
        }
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

    private boolean thereIsALoggedInUser(User loggedInUser) {
        return !(loggedInUser == null);
    }

    private boolean emailAddressIsAlreadyTaken(UserVmGeneral userVmGeneral) {
        Optional<User> user = userInterface.findByEmail(userVmGeneral.getEmailAddress());
        return user.isPresent();
    }

    private boolean newPasswordsDoNotMatch(UserVmGeneral userVmGeneral) {
        return !userVmGeneral.getPassword().equals(userVmGeneral.getPasswordRepeat());
    }

    private boolean newGivenPasswordsAreEqual(UserVmEditPassword userVmEditPassword) {
        return userVmEditPassword.getPassword().equals(userVmEditPassword.getPasswordRepeat());
    }

    private boolean loggedInUserPasswordMatches(UserVmEditPassword userVmEditPassword, User loggedInUser) {
        return passwordEncoder.matches(userVmEditPassword.getCurrentPassword(), loggedInUser.getPassword());
    }

    @GetMapping({"/sysAdmin/users"})
    protected String sysAdminDashboard(@AuthenticationPrincipal User user, Model model) {
        if (user.getUsername().equals("sysAdmin")){
            Collection<? extends User> allUsers = userInterface.findAll();

            model.addAttribute("userList",
                    allUsers.stream().sorted((o1, o2) -> o1.getEmailAddress().compareTo(o2.getEmailAddress())).collect(Collectors.toList()));
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
