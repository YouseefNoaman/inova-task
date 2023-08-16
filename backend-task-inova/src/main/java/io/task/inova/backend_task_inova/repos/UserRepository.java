package io.task.inova.backend_task_inova.repos;

import io.task.inova.backend_task_inova.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID> {
}
