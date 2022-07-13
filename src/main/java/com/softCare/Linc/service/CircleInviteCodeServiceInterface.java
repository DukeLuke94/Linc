package com.softCare.Linc.service;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleInviteCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Project: CircleInviteCodeServiceInterface
 * @author Jan Willem vd Wal on 11-7-2022.
 * Beschrijving:
 */

public interface CircleInviteCodeServiceInterface {

    void save(CircleInviteCode circleInviteCode);

    void delete(CircleInviteCode circleInviteCode);

    Optional<CircleInviteCode> findById(Long circleId);

    Optional<CircleInviteCode> findByCircle_CircleId(Long circleId);

    Optional<CircleInviteCode> findByCircleInviteCode(String circleInviteCode);

    CircleInviteCode getCircleInviteCode(Circle circle);

    String generateCode();
}
