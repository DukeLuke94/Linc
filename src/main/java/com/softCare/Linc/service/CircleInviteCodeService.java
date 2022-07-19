package com.softCare.Linc.service;

import com.softCare.Linc.Repository.CircleInviteCodeRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleInviteCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
                if (circleInviteCode.getDate().isBefore(LocalDate.now())) {
                    circleInviteCode.setExpired(true);
                }
                allInviteCodesList.add(circleInviteCode);
            }
        }
        return Optional.of(allInviteCodesList);
    }

//    @Override
//    public Optional<List<CircleInviteCode>> getAllValidCircleInviteCodes(Circle circle) {
//        Optional<List<CircleInviteCode>> allInviteCodes = circleInviteCodeRepository.findByCircle(circle);
//        List<CircleInviteCode> validInviteCodes = new ArrayList<>();
//        if (allInviteCodes.isPresent()){
//            for (CircleInviteCode circleInviteCode : allInviteCodes.get()) {
//                if (!circleInviteCode.isExpired()) {
//                    validInviteCodes.add(circleInviteCode);
//                }
//            }
//        }
//        return Optional.of(validInviteCodes);
//    }

//    && circleInviteCode.getUserId() <= 0

//    @Override
//    public Optional<List<CircleInviteCode>> getAllExpiredCircleInviteCodes(Circle circle) {
//        Optional<List<CircleInviteCode>> allInviteCodes = circleInviteCodeRepository.findByCircle(circle);
//        List<CircleInviteCode> expiredInviteCodes = new ArrayList<>();
//        if (allInviteCodes.isPresent()){
//            for (CircleInviteCode circleInviteCode : allInviteCodes.get()) {
//                if (circleInviteCode.isExpired()) {
//                    expiredInviteCodes.add(circleInviteCode);
//                }
//            }
//        }
//        return Optional.of(expiredInviteCodes);
//    }

//    || circleInviteCode.getUserId() > 0

    @Override
    public String generateCode() {
        return UUID.randomUUID().toString();
//        Random random = new Random();
//        List<String> colors = List.of(
//                "Yellow", "Green", "Blue", "Violet", "Red", "Orange", "White", "Black", "White");
//        List<String> animals = List.of(
//                "Lynx", "Possum", "Panda", "Eagle", "Rooster", "Lamb", "Buck", "Alpaca", "Chicken", "Toad",
//                "Owl", "Dingo", "Giraffe", "Deer", "Zebra", "Cat", "Hamster", "Gazelle", "Turtle", "Kangaroo", "Bee",
//                "Mouse", "Fox", "Sheep");
//        String result;
//        StringBuilder stringBuilder = new StringBuilder();
//
//        int randomIndexColors = random.nextInt(colors.size());
//        int randomIndexAnimals = random.nextInt(animals.size());
//        int randomNumber = (int) (Math.random() * 100);
//        result = String.valueOf(stringBuilder
//                .append(colors.get(randomIndexColors))
//                .append(animals.get(randomIndexAnimals))
//                .append(randomNumber));
//        return result;
    }
}
