package com.softCare.Linc.Repository;

import com.softCare.Linc.model.Circle;
import com.softCare.Linc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findByCircle(Circle circle);

}
