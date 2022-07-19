package com.softCare.Linc.service;

import com.softCare.Linc.model.User;
import com.softCare.Linc.model.DTO.UserVmEditPassword;
import com.softCare.Linc.model.DTO.UserVmGeneral;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: UserMapperTest
 * @author Jan Willem vd Wal on 30-6-2022.
 * Tests if usermapper class works propperly
 */
class UserMapperTest {

    UserMapper userMapper = new UserMapper();

    @Test
    @DisplayName("userModelToGenericViewmodel")
    void userModelToGenericViewmodel() {
        //arrange:
        User user1 = new User("User1", "test123","a@a.nl", "0909090909");
        User user2 = new User("User2", "123test","b@b.nl", "9090909090");

        //activate:
        UserVmGeneral userVmGeneral1 = userMapper.userToViewModel(user1);
        UserVmGeneral userVmGeneral2 = userMapper.userToViewModel(user2);

        //assert:
        assertInstanceOf(UserVmGeneral.class, userVmGeneral1);
        assertInstanceOf(UserVmGeneral.class, userVmGeneral2);

        assertNotNull(userVmGeneral1.getUsername());
        assertNotNull(userVmGeneral1.getEmailAddress());
        assertNotNull(userVmGeneral1.getPhoneNumber());
        assertNull(userVmGeneral1.getPassword());
        assertNull(userVmGeneral1.getPasswordRepeat());

        assertNotNull(userVmGeneral2.getUsername());
        assertNotNull(userVmGeneral2.getEmailAddress());
        assertNotNull(userVmGeneral2.getPhoneNumber());
        assertNull(userVmGeneral2.getPassword());
        assertNull(userVmGeneral2.getPasswordRepeat());
    }

    @Test
    @DisplayName("genericViewmodelToUserModel")
    void genericViewmodelToUserModel() {
        //arrange:
        UserVmGeneral userVmGeneral3 = new UserVmGeneral("User1","a@a.nl", "0909090909", "test123", "test123");
        UserVmGeneral userVmGeneral4 = new UserVmGeneral("User2","b@b.nl", "9090909090", "123test", "123test");


        //activate:
        User userModel1 = userMapper.userVMToUserModel(userVmGeneral3);
        User userModel2 = userMapper.userVMToUserModel(userVmGeneral4);

        //assert:
        assertInstanceOf(User.class, userModel1);
        assertInstanceOf(User.class, userModel2);

        assertNotNull(userModel1.getUsername());
        assertNotNull(userModel1.getEmailAddress());
        assertNotNull(userModel1.getPhoneNumber());
        assertNotNull(userModel1.getPassword());
        assertNotNull(userModel1.getPasswordRepeat());
        assertNull(userModel1.getAssignedTasks());
        assertNull(userModel1.getCircleMembers());
        assertNull(userModel1.getCurrentPassword());
        assertNotNull(userModel1.getAuthorities());

        assertNotNull(userModel2.getUsername());
        assertNotNull(userModel2.getEmailAddress());
        assertNotNull(userModel2.getPhoneNumber());
        assertNotNull(userModel2.getPassword());
        assertNotNull(userModel2.getPasswordRepeat());
        assertNull(userModel2.getAssignedTasks());
        assertNull(userModel2.getCircleMembers());
        assertNotNull(userModel2.getAuthorities());
        assertNull(userModel2.getCurrentPassword());
    }

    @Test
    @DisplayName("userModelToEditPasswordViewModel")
    void userModelToEditPasswordViewModel() {
        //arrange:
        User user1 = new User("User1", "test123","a@a.nl", "0909090909");
        User user2 = new User("User2", "123test","b@b.nl", "9090909090");

        //activate:
        UserVmEditPassword userVmEditPassword1 = userMapper.userToViewModelEditPassword(user1);
        UserVmEditPassword userVmEditPassword2 = userMapper.userToViewModelEditPassword(user2);

        //assert:
        assertInstanceOf(UserVmEditPassword.class, userVmEditPassword1);
        assertInstanceOf(UserVmEditPassword.class, userVmEditPassword2);

        assertNotNull(userVmEditPassword1.getUsername());
        assertNull(userVmEditPassword1.getCurrentPassword());
        assertNull(userVmEditPassword1.getPassword());
        assertNull(userVmEditPassword1.getPasswordRepeat());

        assertNotNull(userVmEditPassword2.getUsername());
        assertNull(userVmEditPassword2.getCurrentPassword());
        assertNull(userVmEditPassword2.getPassword());
        assertNull(userVmEditPassword2.getPasswordRepeat());
    }

    @Test
    @DisplayName("userEditPasswordViewModelToUserModel")
    void userEditPasswordViewModelToUserModel() {
        //arrange:
        UserVmEditPassword userVmEditPassword3 = new UserVmEditPassword("User1", "123test", "test123", "test123");
        UserVmEditPassword userVmEditPassword4 = new UserVmEditPassword("User2", "test123", "123test", "123test");

        //activate:
        User userModel3 = userMapper.userVmEditPasswordToUserModel(userVmEditPassword3);
        User userModel4 = userMapper.userVmEditPasswordToUserModel(userVmEditPassword4);

        //assert:
        assertInstanceOf(User.class, userModel3);
        assertInstanceOf(User.class, userModel4);

        assertNotNull(userModel3.getCurrentPassword());
        assertNotNull(userModel3.getPassword());
        assertNotNull(userModel3.getPasswordRepeat());
        assertNull(userModel3.getUsername());
        assertNull(userModel3.getEmailAddress());
        assertNull(userModel3.getPhoneNumber());
        assertNull(userModel3.getAssignedTasks());
        assertNull(userModel3.getCircleMembers());
        assertNotNull(userModel3.getAuthorities());

        assertNotNull(userModel4.getCurrentPassword());
        assertNotNull(userModel4.getPassword());
        assertNotNull(userModel4.getPasswordRepeat());
        assertNull(userModel4.getUsername());
        assertNull(userModel4.getEmailAddress());
        assertNull(userModel4.getPhoneNumber());
        assertNull(userModel4.getAssignedTasks());
        assertNull(userModel4.getCircleMembers());
        assertNotNull(userModel4.getAuthorities());
    }
}