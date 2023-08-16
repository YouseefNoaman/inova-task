package io.task.inova.backend_task_inova.service;

import io.task.inova.backend_task_inova.domain.Review;
import io.task.inova.backend_task_inova.domain.Story;
import io.task.inova.backend_task_inova.domain.User;
import io.task.inova.backend_task_inova.model.ReviewDTO;
import io.task.inova.backend_task_inova.repos.ReviewRepository;
import io.task.inova.backend_task_inova.repos.StoryRepository;
import io.task.inova.backend_task_inova.repos.UserRepository;
import io.task.inova.backend_task_inova.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    public ReviewService(final ReviewRepository reviewRepository,
            final StoryRepository storyRepository, final UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> findAll() {
        final List<Review> reviews = reviewRepository.findAll(Sort.by("id"));
        return reviews.stream()
                .map(review -> mapToDTO(review, new ReviewDTO()))
                .toList();
    }

    public ReviewDTO get(final UUID id) {
        return reviewRepository.findById(id)
                .map(review -> mapToDTO(review, new ReviewDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ReviewDTO reviewDTO) {
        final Review review = new Review();
        mapToEntity(reviewDTO, review);
        return reviewRepository.save(review).getId();
    }

    public void update(final UUID id, final ReviewDTO reviewDTO) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reviewDTO, review);
        reviewRepository.save(review);
    }

    public void delete(final UUID id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO mapToDTO(final Review review, final ReviewDTO reviewDTO) {
        reviewDTO.setId(review.getId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setStoryId(review.getStoryId() == null ? null : review.getStoryId().getId());
        reviewDTO.setUserId(review.getUserId() == null ? null : review.getUserId().getId());
        return reviewDTO;
    }

    private Review mapToEntity(final ReviewDTO reviewDTO, final Review review) {
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        final Story storyId = reviewDTO.getStoryId() == null ? null : storyRepository.findById(reviewDTO.getStoryId())
                .orElseThrow(() -> new NotFoundException("storyId not found"));
        review.setStoryId(storyId);
        final User userId = reviewDTO.getUserId() == null ? null : userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("userId not found"));
        review.setUserId(userId);
        return review;
    }

}
