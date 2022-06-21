package com.softCare.Linc.service;

import com.softCare.Linc.Repository.UserRepository;
import com.softCare.Linc.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Project: UserService
 * @author Jan Willem vd Wal on 21-6-2022.
 * Beschrijving:
 */
@Service
public class LincUserDetailService implements UserDetailsService, LincUserDetailServiceInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public LincUserDetailService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName).orElseThrow(
                () -> new UsernameNotFoundException("User with name " + userName + " was not found."));
    }

    @Override
    public Collection<? extends User> findAll() {
        return userRepository.findAll();
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
