package com.softCare.Linc.service;

import com.softCare.Linc.Repository.CircleMemberRepository;
import com.softCare.Linc.Repository.CircleRepository;
import com.softCare.Linc.Repository.UserRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.User;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CircleMemberService implements CircleMemberServiceInterface {

    private final CircleMemberRepository circleMemberRepository;
    private final UserRepository userRepository;
    private final CircleRepository circleRepository;

    public CircleMemberService(CircleMemberRepository circleMemberRepository, UserRepository userRepository, CircleRepository circleRepository) {
        this.circleMemberRepository = circleMemberRepository;
        this.userRepository = userRepository;
        this.circleRepository = circleRepository;
    }

    @Override
    public void save(CircleMember circleMember) {
        circleMemberRepository.save(circleMember);

    }

    public boolean isMember(User user, Circle circle){
        return circleMemberRepository.findByUserUserIdAndCircle_CircleId(user.getUserId(), circle.getCircleId()).isPresent();

    }

    @Override
    public boolean isAdmin(User user, Circle circle) {
        Optional<CircleMember> circleMember = circleMemberRepository.findByUserUserIdAndCircle_CircleId(user.getUserId(),circle.getCircleId());
        return circleMember.map(CircleMember::isAdmin).orElse(false);
    }

    @Override
    public List<Circle> findAllCirclesWhereMemberOf(User user) {
        Optional<List<CircleMember>> memberList = circleMemberRepository.findByUserUserId(user.getUserId());
        List<Circle> circleList = new ArrayList<>();
        if (memberList.isPresent()){
            for (CircleMember member : memberList.get()) {
                Long id = member.getCircle().getCircleId();
                Optional<Circle> circle = circleRepository.findById(id);
                circle.ifPresent(circleList::add);
            }
    }
        return circleList;
    }

    @Override
    public List<User> findAllMembers(Circle circle) {
        Optional<List<CircleMember>> circleMemberList = circleMemberRepository.findByCircleCircleId(circle.getCircleId());
        List<User> userList = new ArrayList<>();
        if (circleMemberList.isPresent()){
            for (CircleMember circleMember : circleMemberList.get()) {
                userList.add(userRepository.findById(circleMember.getUser().getUserId()).get());
            }
        }
        return userList;
    }
}
