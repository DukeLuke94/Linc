package com.softCare.Linc.Repository;

import com.softCare.Linc.model.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CircleRepository extends JpaRepository<Circle, Long> {

    Circle findByCircleName(String name);
}
