package com.softCare.Linc.service;

import com.softCare.Linc.Repository.CircleRepository;
import com.softCare.Linc.model.Circle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CircleService implements  CircleServiceInterface{
    private final CircleRepository circleRepository;
    private final CircleMapper circleMapper;

    public CircleService(CircleRepository circleRepository, CircleMapper circleMapper) {
        this.circleRepository = circleRepository;
        this.circleMapper = circleMapper;
    }

    @Override
    public Object findAll() {
        List<Circle> circlesObjects = circleRepository.findAll();
        return new ArrayList<>(circlesObjects);
    }

    @Override
    public Optional<Circle> findById(Long circleId) {
        Optional<Circle> circle = circleRepository.findById(circleId);
        return circle.map(circleMapper::circleToModelView);
    }

    @Override
    public void save(Circle circle) {
        circleRepository.save(circle);
    }

    @Override
    public void delete(Circle circle) {
        circleRepository.delete(circle);
    }

    @Override
    public Circle findByCircleName(String name) {
        return circleRepository.findByCircleName(name);
    }


}
