package com.softCare.Linc.controller;

import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final LincUserDetailServiceInterface lincUserDetailServiceInterface;
//    private final PasswordEncoder passwordEncoder;

    public UserController(LincUserDetailServiceInterface lincUserDetailServiceInterface) {
        this.lincUserDetailServiceInterface = lincUserDetailServiceInterface;
//        this.passwordEncoder = passwordEncoder;
    }
}
