package com.softCare.Linc.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity @Getter @Setter @Proxy(lazy = false)
public class User implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Transient
    private String currentPassword;
    @Transient
    private String passwordRepeat;

    @OneToMany (mappedBy = "user")
    private List<Task> assignedTasks;

    private String emailAddress;
    private String phoneNumber;

    @OneToMany(mappedBy = "user",  cascade = ALL,fetch = FetchType.EAGER)
    private List<CircleMember> circleMembers;



    public User(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
