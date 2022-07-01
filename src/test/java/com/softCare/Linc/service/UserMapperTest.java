package com.softCare.Linc.service;

import com.softCare.Linc.model.User;
import com.softCare.Linc.model.UserVmGeneral;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: UserMapperTest
 * @author Jan Willem vd Wal on 30-6-2022.
 * Beschrijving:
 */
class UserMapperTest {

    PasswordEncoder passwordEncoder;
    final UserMapper userMapper;

    UserMapperTest(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Test
    @DisplayName("userModelNaarGeneriekViewmodel")
    void userModelNaarGeneriekViewmodel() {
        //arrange:
        User user1 = new User("User1", passwordEncoder.encode("test123"),"a@a.nl", "0909090909");
        User user2 = new User("User2", passwordEncoder.encode("123test"),"b@b.nl", "0909090909");

        //activate:
        UserVmGeneral userVmGeneral1 = userMapper.userToViewModel(user1);
        UserVmGeneral userVmGeneral2 = userMapper.userToViewModel(user2);

        //assert:
        assertInstanceOf(UserVmGeneral.class, userVmGeneral1);
        assertInstanceOf(UserVmGeneral.class, userVmGeneral2);
    }

//    @Test
//    void userVMToUserModel() {
//        //arrange:
//
//        //activate:
//
//        //assert:
//    }
//
//    @Test
//    void userToViewModelEditPassword() {
//        //arrange:
//
//        //activate:
//
//        //assert:
//    }
//
//    @Test
//    void userVmEditPasswordToUserModel() {
//        //arrange:
//
//        //activate:
//
//        //assert:
//    }
}