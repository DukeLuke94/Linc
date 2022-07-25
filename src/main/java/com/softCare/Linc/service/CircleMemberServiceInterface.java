package com.softCare.Linc.service;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;

import java.util.List;
import java.util.Optional;

public interface CircleMemberServiceInterface {


    void save(CircleMember circleMember);

    boolean isMember(User user, Circle circle);
    boolean isAdmin(User user, Circle circle);

    boolean isAdminOfTask(User user, Task task);

    List<Circle> findAllCirclesWhereMemberOf(User user);

    List<User> findAllMembers(Circle circle);

    void deleteByUserIdAndCircleId(Long userId, Long circleId);

    Optional<CircleMember> findByUserIdAndCircleId(Long userId, Long circleId);

    void delete(CircleMember circleMember);

    Optional<List<CircleMember>> findCircleMembers(Circle circle);

    Optional<CircleMember> findById(Long circleMemberId);
}