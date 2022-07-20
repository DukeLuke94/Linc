package com.softCare.Linc.service;

import com.softCare.Linc.model.DTO.UserVMEdit;
import com.softCare.Linc.model.User;
import com.softCare.Linc.model.DTO.UserVmEditPassword;
import com.softCare.Linc.model.DTO.UserVmGeneral;
import org.springframework.stereotype.Service;

/**
 * Project: UserMapper
 * @author Jan Willem vd Wal on 21-6-2022.
 * Turns usermodels to user viewmodels to build up view and vice versa.
 */

@Service
public class UserMapper {

    public UserVmGeneral userToViewModel(User user){
        UserVmGeneral userVmGeneral = new UserVmGeneral();
        userVmGeneral.setUsername(user.getUsername());
        userVmGeneral.setEmailAddress(user.getEmailAddress());
        userVmGeneral.setPhoneNumber(user.getPhoneNumber());
        return userVmGeneral;
    }

    public UserVMEdit userEditToViewModel(User user) {
        UserVMEdit userVMEdit = new UserVMEdit();
        userVMEdit.setUsername(user.getUsername());
        userVMEdit.setEmailAddress(user.getEmailAddress());
        userVMEdit.setPhoneNumber(user.getPhoneNumber());
        return userVMEdit;
    }

    public User userVMEditDetailsToUserModel(UserVMEdit userVMEdit) {
        User user = new User();
        user.setUsername(userVMEdit.getUsername());
        user.setEmailAddress(userVMEdit.getEmailAddress());
        user.setPhoneNumber(userVMEdit.getPhoneNumber());
        return user;
    }

    public User userVMToUserModel(UserVmGeneral userVmGeneral) {
        User user = new User();
        user.setUsername(userVmGeneral.getUsername());
        user.setEmailAddress(userVmGeneral.getEmailAddress());
        user.setPhoneNumber(userVmGeneral.getPhoneNumber());
        user.setPassword(userVmGeneral.getPassword());
        user.setPasswordRepeat(userVmGeneral.getPasswordRepeat());
        return user;
    }

    public UserVmEditPassword userToViewModelEditPassword(User user){
        UserVmEditPassword userVmEditPassword = new UserVmEditPassword();
        userVmEditPassword.setUsername(user.getUsername());
        return userVmEditPassword;
    }

    public User userVmEditPasswordToUserModel(UserVmEditPassword userVmEditPassword) {
        User user = new User();
        user.setCurrentPassword(userVmEditPassword.getCurrentPassword());
        user.setPassword(userVmEditPassword.getPassword());
        user.setPasswordRepeat(userVmEditPassword.getPasswordRepeat());
        return user;
    }


}
