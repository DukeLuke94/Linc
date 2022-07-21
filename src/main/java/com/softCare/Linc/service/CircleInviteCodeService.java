package com.softCare.Linc.service;

import com.softCare.Linc.Repository.CircleInviteCodeRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleInviteCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project: CircleInviteCodeService
 * @author Jan Willem vd Wal on 11-7-2022.
 * Shows the functionality of the methods of CircleInviteCodeServiceInterface
 */
@Service
public class CircleInviteCodeService implements CircleInviteCodeServiceInterface {

    private final CircleInviteCodeRepository circleInviteCodeRepository;

    public CircleInviteCodeService(CircleInviteCodeRepository circleInviteCodeRepository) {
        this.circleInviteCodeRepository = circleInviteCodeRepository;
    }

    @Override
    public void save(CircleInviteCode circleInviteCode) {
        circleInviteCodeRepository.save(circleInviteCode);
    }

    @Override
    public void delete(CircleInviteCode circleInviteCode) {
        circleInviteCodeRepository.delete(circleInviteCode);
    }

    @Override
    public Optional<CircleInviteCode> findById(Long circleInviteCodeId) {
        return circleInviteCodeRepository.findById(circleInviteCodeId);
    }

    @Override
    public Optional<CircleInviteCode> findByCircle_CircleId(Long circleId) {
        return circleInviteCodeRepository.findByCircle_CircleId(circleId);
    }

    @Override
    public Optional<CircleInviteCode> findByCircleInviteCode(String circleInviteCode) {
        return circleInviteCodeRepository.findByInviteCode(circleInviteCode);
    }

    @Override
    public CircleInviteCode getCircleInviteCode(Circle circle) {
        return new CircleInviteCode(generateCode());
    }

    @Override
    public Optional<List<CircleInviteCode>> getAllCircleInviteCodes(Circle circle) {
        Optional<List<CircleInviteCode>> allInviteCodes = circleInviteCodeRepository.findByCircle(circle);
        List<CircleInviteCode> allInviteCodesList = new ArrayList<>();
        if (allInviteCodes.isPresent()){
            for (CircleInviteCode circleInviteCode : allInviteCodes.get()) {
                if (circleInviteCode.getDate().isBefore(LocalDate.now()) || circleInviteCode.getUserId() != null) {
                    circleInviteCode.setExpired(true);
                    allInviteCodesList.add(circleInviteCode);
                } else {
                    circleInviteCode.setExpired(false);
                    allInviteCodesList.add(circleInviteCode);
                }
            }
        }
        return Optional.of(allInviteCodesList.stream().sorted(Comparator.comparing(CircleInviteCode::isExpired)).collect(Collectors.toList()));
    }

    @Override
    public String generateCode() {
        return UUID.randomUUID().toString();
    }
}
