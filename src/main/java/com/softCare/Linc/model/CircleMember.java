package com.softCare.Linc.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Lucas Blumers
 *
 * Describes the relations between a user and a CircleMember
 */
@Entity @Getter @Setter
public class CircleMember implements Comparable<CircleMember> {

    @Id
    @GeneratedValue
    private Long circleMemberId;

    @ManyToOne
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Circle circle;

    private boolean isClient;
    private boolean isAdmin;


    public CircleMember(User user, Circle circle, boolean isClient, boolean isAdmin) {
        this.user = user;
        this.circle = circle;
        this.isClient = isClient;
        this.isAdmin = isAdmin;
    }

    public CircleMember(User user, Circle circle) {
        this.user = user;
        this.circle = circle;
        this.isClient = false;
        this.isAdmin = false;
    }

    public CircleMember() {
        this.user = new User();
        this.circle = new Circle();
        this.isAdmin = false;
        this.isClient = false;
    }


    @Override
    public int compareTo(CircleMember o) {
        if (o.isAdmin){
            return 1;
        } else if (o.isClient){
            return 0;
        }
        return -1;
    }
}
