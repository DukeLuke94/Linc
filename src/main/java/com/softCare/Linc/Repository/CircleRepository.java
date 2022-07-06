package com.softCare.Linc.Repository;

import com.softCare.Linc.model.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CircleRepository extends JpaRepository<Circle, Long> {
    Circle findByCircleName(String name);
}
