package com.softCare.Linc.Repository;

import com.softCare.Linc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
