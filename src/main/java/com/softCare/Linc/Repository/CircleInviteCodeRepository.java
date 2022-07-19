package com.softCare.Linc.Repository;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleInviteCode;
import com.softCare.Linc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Project: CircleInviteCodeRepository
 *
 * @author Jan Willem vd Wal on 11-7-2022.
 * Beschrijving:
 */
public interface CircleInviteCodeRepository extends JpaRepository<CircleInviteCode, Long> {

    Optional<CircleInviteCode> findById(Long aLong);
    Optional<CircleInviteCode> findByCircle_CircleId(Long circleId);
    Optional<List<CircleInviteCode>> findByCircle(Circle circle);
    Optional<CircleInviteCode> findByInviteCode(String circleInviteCode);
}
