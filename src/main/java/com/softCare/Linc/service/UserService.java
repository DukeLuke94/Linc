package com.softCare.Linc.service;

import com.softCare.Linc.Repository.UserRepository;
import com.softCare.Linc.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Project: UserService
 * @author Jan Willem vd Wal on 21-6-2022.
 * Beschrijving:
 */

@Service
public class UserService implements LincUserDetailServiceInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        return new ArrayList<>(userList);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
