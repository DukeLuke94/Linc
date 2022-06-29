package com.softCare.Linc.service;

import com.softCare.Linc.model.User;
import com.softCare.Linc.model.UserVM;
import org.springframework.stereotype.Service;

/**
 * Project: UserMapper
 * @author Jan Willem vd Wal on 21-6-2022.
 * Turns usermodels to user viewmodels to build up view and vice versa.
 */

@Service
public class UserMapper {
    public UserVM userToViewModel(User user){
        UserVM userVM = new UserVM();
        userVM.setUserId(user.getUserId());
        userVM.setUsername(user.getUsername());
        userVM.setEmailAddress(user.getEmailAddress());
        userVM.setPhoneNumber(user.getPhoneNumber());
        userVM.setAssignedTasks(user.getAssignedTasks());
        return userVM;
    }

    public User userVMToUserModel(UserVM userVM) {
        User user = new User();
        user.setUserId(userVM.getUserId());
        user.setUsername(userVM.getUsername());
        user.setEmailAddress(userVM.getEmailAddress());
        user.setPhoneNumber(userVM.getPhoneNumber());
        user.setCurrentPassword(userVM.getCurrentPassword());
        user.setPassword(userVM.getPassword());
        user.setPasswordRepeat(userVM.getPasswordRepeat());
        user.setAssignedTasks(userVM.getAssignedTasks());
        return user;
    }
}
