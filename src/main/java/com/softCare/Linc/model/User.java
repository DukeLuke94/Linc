package com.softCare.Linc.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Proxy(lazy = false)

public class User implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;
    @NotNull @Column(unique = true)
    private String username;

    @NotNull
    private String password;
    @Transient
    private String passwordRepeat;
    @Transient
    private String currentPassword;

    @OneToMany (mappedBy = "user")
    private List<Task> assignedTasks;
    @NotNull
    private String emailAddress;
    @NotNull
    private String phoneNumber;

    @OneToMany(mappedBy = "user",  cascade = ALL,fetch = FetchType.EAGER)
    private List<CircleMember> circleMembers;

    public User(String username, String password, String emailAddress, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
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
