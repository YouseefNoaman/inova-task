package io.task.inova.backend_task_inova.repos;

import io.task.inova.backend_task_inova.domain.Review;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
