package com.softCare.Linc.configuration;

import com.softCare.Linc.service.LincUserDetailServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Project: LincSecurityConfiguration
 * @author Jan Willem vd Wal on 21-6-2022.
 * Configure the security settings for LinC
 */

@Configuration
public class LincSecurityConfiguration {

//    final LincUserDetailServiceInterface lincUserDetailServiceInterface;
//
//    public LincSecurityConfiguration(LincUserDetailServiceInterface lincUserDetailServiceInterface) {
//        this.lincUserDetailServiceInterface = lincUserDetailServiceInterface;
//    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                    .antMatchers("/css/**", "/webjars/**").permitAll()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
            )
                .formLogin().and()
                .logout().logoutSuccessUrl("/");
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService((UserDetailsService) lincUserDetailServiceInterface);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
}
