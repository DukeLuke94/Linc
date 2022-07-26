package com.softCare.Linc.service;

import com.softCare.Linc.Repository.CircleMemberRepository;
import com.softCare.Linc.Repository.CircleRepository;
import com.softCare.Linc.Repository.TaskRepository;
import com.softCare.Linc.Repository.UserRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleMember;
import com.softCare.Linc.model.Task;
import com.softCare.Linc.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CircleMemberService implements CircleMemberServiceInterface {

    private final CircleMemberRepository circleMemberRepository;
    private final UserRepository userRepository;
    private final CircleRepository circleRepository;

    private final TaskRepository taskRepository;

    public CircleMemberService(CircleMemberRepository circleMemberRepository, UserRepository userRepository, CircleRepository circleRepository, TaskRepository taskRepository) {
        this.circleMemberRepository = circleMemberRepository;
        this.userRepository = userRepository;
        this.circleRepository = circleRepository;
        this.taskRepository = taskRepository;
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


    public boolean isAdminOfTask(User user, Task task) {
        Optional<Task> currentTask = taskRepository.findById(task.getTaskId());
        Optional<Circle> circle = circleRepository.findById(currentTask.get().getCircle().getCircleId());
        if (circle.isPresent()){
            return isAdmin(user,circle.get());
        }
        return false;
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
            Collections.sort(circleMemberList.get());

            for (CircleMember circleMember : circleMemberList.get()) {
                System.out.println(circleMember.isAdmin());
                User user = userRepository.findById(circleMember.getUser().getUserId()).get();
                if (user.getProfilePicture()!=null){
                    user.setProfilePictureString(Base64.getEncoder().encodeToString(user.getProfilePicture()));
                }

                userList.add(user);
            }
        }

        return userList;
    }

    @Override
    public void deleteByUserIdAndCircleId(Long userId, Long circleId) {
        Optional<CircleMember> member = circleMemberRepository.findByUserUserIdAndCircle_CircleId(userId,circleId);
        member.ifPresent(circleMember -> circleMemberRepository.deleteById(circleMember.getCircleMemberId()));

    }

    @Override
    public Optional<CircleMember> findByUserIdAndCircleId(Long userId, Long circleId) {
        return circleMemberRepository.findByUserUserIdAndCircle_CircleId(userId,circleId);
    }

    @Override
    public void delete(CircleMember circleMember) {
        circleMemberRepository.delete(circleMember);

    }

    @Override
    public Optional<List<CircleMember>> findCircleMembers(Circle circle) {
        return circleMemberRepository.findByCircleCircleId(circle.getCircleId());
    }

    @Override
    public Optional<CircleMember> findById(Long circleMemberId) {
        return circleMemberRepository.findByCircleMemberId(circleMemberId);
    }
}