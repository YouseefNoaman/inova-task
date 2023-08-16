package io.task.inova.backend_task_inova.repos;

import io.task.inova.backend_task_inova.domain.Story;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoryRepository extends JpaRepository<Story, UUID> {

    List<Story> findAllByUserId(UUID uuid);

    Page<Story> findAll(Pageable pageable);
}
