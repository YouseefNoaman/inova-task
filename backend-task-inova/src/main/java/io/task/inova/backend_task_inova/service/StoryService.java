package io.task.inova.backend_task_inova.service;

import io.task.inova.backend_task_inova.domain.Story;
import io.task.inova.backend_task_inova.domain.User;
import io.task.inova.backend_task_inova.model.StoryDTO;
import io.task.inova.backend_task_inova.repos.StoryRepository;
import io.task.inova.backend_task_inova.repos.UserRepository;
import io.task.inova.backend_task_inova.util.NotFoundException;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    public StoryService(final StoryRepository storyRepository,
            final UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    public List<StoryDTO> findAll() {
        final List<Story> storys = storyRepository.findAll(Sort.by("id"));
        return storys.stream()
                .map(story -> mapToDTO(story, new StoryDTO()))
                .toList();
    }

    public List<StoryDTO> findAllPages(Pageable pageable) {
        final List<Story> stories = storyRepository.findAll(pageable).getContent();
        return stories.stream()
                .map(story -> mapToDTO(story, new StoryDTO()))
                .toList();
    }

    public List<StoryDTO> findAllByUser(UUID id) {
        final List<Story> stories = storyRepository.findAllByUserId(id);
        return stories.stream()
                .map(story -> mapToDTO(story, new StoryDTO()))
                .toList();
    }

    public StoryDTO get(final UUID id) {
        return storyRepository.findById(id)
                .map(story -> mapToDTO(story, new StoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final StoryDTO storyDTO) {
        final Story story = new Story();
        mapToEntity(storyDTO, story);
        return storyRepository.save(story).getId();
    }

    public void update(final UUID id, final StoryDTO storyDTO) {
        final Story story = storyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(storyDTO, story);
        storyRepository.save(story);
    }

    public void delete(final UUID id) {
        storyRepository.deleteById(id);
    }

    private StoryDTO mapToDTO(final Story story, final StoryDTO storyDTO) {
        storyDTO.setId(story.getId());
        storyDTO.setTitle(story.getTitle());
        storyDTO.setBody(story.getBody());
        storyDTO.setUserId(story.getUserId() == null ? null : story.getUserId().getId());
        return storyDTO;
    }

    private Story mapToEntity(final StoryDTO storyDTO, final Story story) {
        story.setTitle(storyDTO.getTitle());
        story.setBody(storyDTO.getBody());
        final User userId = storyDTO.getUserId() == null ? null : userRepository.findById(storyDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("userId not found"));
        story.setUserId(userId);
        return story;
    }

}
