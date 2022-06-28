package com.softCare.Linc.Repository;

import com.softCare.Linc.model.CircleMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CircleMemberRepository extends JpaRepository<CircleMember, Long> {

    Optional<CircleMember> findByUserUserIdAndCircle_CircleId(Long user_userId, Long circle_circleId);

    Optional<List<CircleMember>> findByUserUserId(Long user_userId);
}
