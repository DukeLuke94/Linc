package com.softCare.Linc.controller;

import com.softCare.Linc.model.*;
import com.softCare.Linc.model.DTO.UserVMEdit;
import com.softCare.Linc.model.DTO.UserVmEditPassword;
import com.softCare.Linc.model.DTO.UserVmGeneral;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {
    public final String YOU_VE_BEEN_LOGGED_OUT = "You've been logged out";
    public final String LOGIN_TO_CONTINUE = "Registration successfull! Login to continue";
    public final String EDIT_SUCCESSFUL = "Password edit successful, please login with your new password";
    public final String EMAIL_ALREADY_IN_USE = "This Email-address is already in use";
    public final String CURRENT_PASSWORD_IS_NOT_CORRECT = "The current password is not correct";
    private final String PASSWORD_REPEAT_NO_MATCH = "The newly entered passwords are not an exact match";

    private final String PASSWORDS_NOT_GIVEN = "The password-fields cannot be empty";

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

    @PostMapping("/logout")
    protected String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", YOU_VE_BEEN_LOGGED_OUT);
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
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (emailAddressIsAlreadyTaken(userVmGeneral) && userVmGeneral.getEmailAddress().contains("@")) {
            model.addAttribute("errorMessage", EMAIL_ALREADY_IN_USE);
            model.addAttribute("inviteCode", inviteCode);
            return "userForm";
        }
        if (newPasswordsDoNotMatch(userVmGeneral)) {
            model.addAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
            model.addAttribute("inviteCode", inviteCode);
            return "userForm";
        } else if (!result.hasErrors()) {
            User newUser = getNewUserFromUserVMAndSaveNewUser(userVmGeneral);
            boolean isInviteCodeConnectedToCircle =
                    circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).isPresent();
            if (isInviteCodeConnectedToCircle) {
                setUserIdOnUsedCircleInviteCode(inviteCode, newUser);
                addNewUserToCircleOfInviteCode(inviteCode, newUser);
            }
            redirectAttributes.addFlashAttribute("successMessage", LOGIN_TO_CONTINUE);
            return "redirect:/login";
        }
        if (inviteCode.contains("-")) {
            model.addAttribute("inviteCode", inviteCode);
        }
        return "userForm";
    }

    private User getNewUserFromUserVMAndSaveNewUser(UserVmGeneral userVmGeneral) {
        User newUser = userMapper.userVMToUserModel(userVmGeneral);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userInterface.save(newUser);
        return newUser;
    }

    private void setUserIdOnUsedCircleInviteCode(String inviteCode, User newUser) {
        CircleInviteCode circleInviteCode = circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).get();
        circleInviteCode.setUserId(newUser.getUserId());
        circleInviteCodeServiceInterface.save(circleInviteCode);
    }

    private void addNewUserToCircleOfInviteCode(String inviteCode, User newUser) {
        Circle circleToAddNewUserTo = circleInviteCodeServiceInterface.findByCircleInviteCode(inviteCode).get().getCircle();
        circleMemberServiceInterface.save(new CircleMember(newUser, circleToAddNewUserTo,false,false));
    }

    @GetMapping({"/user/edit/details"})
    protected String editUser(Authentication authentication, Model model, @AuthenticationPrincipal User loggedInUser) {
        User user = (User) userInterface.loadUserByUsername(authentication.getName());
        UserVMEdit userVMEdit = userMapper.userEditToViewModel(user);
        model.addAttribute("userVM", userVMEdit);
        return "editUserDetails";
    }

    @PostMapping({"/user/edit/details"})
    protected String editUserDetails(@Valid @ModelAttribute("userVM") UserVMEdit userVMEdit,
                                     BindingResult result,
                                     Model model,
                                     @AuthenticationPrincipal User loggedInUser,
                                     RedirectAttributes redirectAttributes) {

        //Set booleans for check if email is already in use
        boolean wantsToUpdateEmail = !Objects.equals(userVMEdit.getEmailAddress(), loggedInUser.getEmailAddress());
        boolean validEmail = userVMEdit.getEmailAddress().contains("@");
        boolean emailAvailable = !emailAddressIsAlreadyTakenEditUserDetails(userVMEdit);

        if (wantsToUpdateEmail && validEmail && !emailAvailable) {
            model.addAttribute("errorMessage", EMAIL_ALREADY_IN_USE);
            return "editUserDetails";
        }
        //Give feedback for other errors then email already in use
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", result.getAllErrors());
            return "editUserDetails";
        } else {
            //set attributes for logged in user so edited fields change also in view
            setLoggedInUser(userVMEdit, loggedInUser);
            User user = setEditedUser(userVMEdit, loggedInUser);

            userInterface.save(user);
            userInterface.save(loggedInUser);
        }
        return "redirect:/user/profile";
    }

    private void setLoggedInUser(UserVMEdit userVMEdit, User loggedInUser) {
        loggedInUser.setUsername(userVMEdit.getUsername());
        loggedInUser.setEmailAddress(userVMEdit.getEmailAddress());
        loggedInUser.setPhoneNumber(userVMEdit.getPhoneNumber());
    }

    private boolean emailAddressIsAlreadyTakenEditUserDetails(UserVMEdit UserVMEdit) {
        Optional<User> user = userInterface.findByEmail(UserVMEdit.getEmailAddress());
        return user.isPresent();
    }

    @GetMapping({"/user/edit/password"})
    protected String editUserPassword(Authentication authentication, Model model) {
        User user = (User) userInterface.loadUserByUsername(authentication.getName());
        UserVmEditPassword userVmEditPassword = userMapper.userToViewModelEditPassword(user);
        model.addAttribute("userVM", userVmEditPassword);
        return "editPasswordForm";
    }


    @PostMapping({"/user/edit/password"})
    protected String editUserPassword(@Valid @ModelAttribute("userVM") UserVmEditPassword userVmEditPassword,
                                      BindingResult result,
                                      @AuthenticationPrincipal User loggedInUser,
                                      RedirectAttributes redirectAttributes) {
        if (allFieldsAreBlank(userVmEditPassword)) {
            return "editPasswordForm";
        }
        if (loggedInUserPasswordIsNoMatch(userVmEditPassword, loggedInUser)) {
            redirectAttributes.addFlashAttribute("errorMessage", CURRENT_PASSWORD_IS_NOT_CORRECT);
            return "redirect:/user/edit/password";
        }
        if (newGivenPasswordsAreNotEqual(userVmEditPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", PASSWORD_REPEAT_NO_MATCH);
            return "redirect:/user/edit/password";
        }
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", result.getAllErrors());
            return "editPasswordForm";
        }
        User user = setUpdatedUserWithNewPassword(userVmEditPassword, loggedInUser);
        userInterface.save(user);
        redirectAttributes.addFlashAttribute("succesMessage", EDIT_SUCCESSFUL);
        return "redirect:/user/edit/password";
    }

    private boolean allFieldsAreBlank(UserVmEditPassword userVmEditPassword) {
        return userVmEditPassword.getCurrentPassword().isBlank() && userVmEditPassword.getPassword().isBlank() && userVmEditPassword.getPasswordRepeat().isBlank();
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String currentUserName(@AuthenticationPrincipal User loggedInUser, Authentication authentication, Model model) {
        model.addAttribute("user", userInterface.loadUserByUsername(authentication.getName()));
        if (loggedInUser.getProfilePicture() != null) {
            model.addAttribute("profilePicture", Base64.getEncoder().encodeToString(loggedInUser.getProfilePicture()));
        }
        return "userProfile";
    }

    private boolean loggedInUserPasswordIsNoMatch(UserVmEditPassword userVmEditPassword, User loggedInUser) {
        return !passwordEncoder.matches(userVmEditPassword.getCurrentPassword(), loggedInUser.getPassword());

    }

    private boolean newGivenPasswordsAreNotEqual(UserVmEditPassword userVmEditPassword) {
        return !userVmEditPassword.getPassword().equals(userVmEditPassword.getPasswordRepeat());
    }

    private User setUpdatedUserWithNewPassword(UserVmEditPassword userVmEditPassword, User loggedInUser) {
        User user = userMapper.userVmEditPasswordToUserModel(userVmEditPassword);
        user.setUserId(loggedInUser.getUserId());
        user.setUsername(loggedInUser.getUsername());
        user.setEmailAddress(loggedInUser.getEmailAddress());
        user.setPhoneNumber(loggedInUser.getPhoneNumber());
        user.setAssignedTasks(loggedInUser.getAssignedTasks());
        user.setProfilePicture(loggedInUser.getProfilePicture());
        user.setPassword(passwordEncoder.encode(userVmEditPassword.getPassword()));
        return user;
    }

    private User setEditedUser(UserVMEdit userVMEdit, User loggedInUser) {
        User user = userMapper.userVMEditDetailsToUserModel(userVMEdit);
        user.setUserId(loggedInUser.getUserId());
        user.setUsername(loggedInUser.getUsername());
        user.setEmailAddress(loggedInUser.getEmailAddress());
        user.setPhoneNumber(loggedInUser.getPhoneNumber());
        user.setAssignedTasks(loggedInUser.getAssignedTasks());
        user.setProfilePicture(loggedInUser.getProfilePicture());
        user.setPassword(loggedInUser.getPassword());
        return user;
    }

    private boolean emailAddressIsAlreadyTaken(UserVmGeneral userVmGeneral) {
        Optional<User> user = userInterface.findByEmail(userVmGeneral.getEmailAddress());
        return user.isPresent();
    }

    private boolean newPasswordsDoNotMatch(UserVmGeneral userVmGeneral) {
        return  !userVmGeneral.getPassword().equals(userVmGeneral.getPasswordRepeat()) &&
                !userVmGeneral.getPassword().isBlank() &&
                !userVmGeneral.getPasswordRepeat().isBlank() &&
                userVmGeneral.getPassword().length() >= 6 &&
                userVmGeneral.getPasswordRepeat().length() >= 6;
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
