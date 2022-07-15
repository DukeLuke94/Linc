package com.softCare.Linc.service;

import com.softCare.Linc.Repository.CircleInviteCodeRepository;
import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.CircleInviteCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Project: CircleInviteCodeService
 * @author Jan Willem vd Wal on 11-7-2022.
 * Beschrijving:
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
        boolean isCodePresent =
                circleInviteCodeRepository.findByCircle_CircleId(circle.getCircleId()).isPresent();
        if (isCodePresent) {
            return circleInviteCodeRepository.findByCircle_CircleId(circle.getCircleId()).get();
        }
        return new CircleInviteCode(generateCode());
    }

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
