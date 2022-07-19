package com.softCare.Linc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Project: CircleInviteCode
 *
 * @author Jan Willem vd Wal on 11-7-2022.
 * Beschrijving:
 */

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Proxy(lazy = false)
public class CircleInviteCode {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private String inviteCode;

    private Long userId;

    private boolean isExpired;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Circle circle;

    public CircleInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
