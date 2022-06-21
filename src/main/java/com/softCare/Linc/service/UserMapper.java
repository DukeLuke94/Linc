package com.softCare.Linc.service;

import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import org.springframework.stereotype.Service;

/**
 * Project: UserMapper
 * @author Jan Willem vd Wal on 21-6-2022.
 * Beschrijving:
 */
@Service
public class UserMapper {

    public User userToViewModel(User user){
        User viewmodel = new User();
        viewmodel.setUserId(user.getUserId());
        viewmodel.setUserName(user.getUsername());
        return viewmodel;
    }
}
