package io.task.inova.backend_task_inova.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.task.inova.backend_task_inova.model.StoryDTO;
import io.task.inova.backend_task_inova.service.StoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/stories", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoryResource {

    private final StoryService storyService;

    public StoryResource(final StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStorys() {
        return ResponseEntity.ok(storyService.findAll());
    }

    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStoriesInPages(Pageable pageable) {
        return ResponseEntity.ok(storyService.findAllPages(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStory(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(storyService.get(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryByPage(@PathVariable(name = "id") final UUID id, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(storyService.get(id, paging));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createStory(@RequestBody @Valid final StoryDTO storyDTO) {
        final UUID createdId = storyService.create(storyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateStory(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final StoryDTO storyDTO) {
        storyService.update(id, storyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteStory(@PathVariable(name = "id") final UUID id) {
        storyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<?> returnStoriesByUser(@PathVariable UUID id){
        return new ResponseEntity<>(storyService.findAllByUser(id), HttpStatus.OK);
    }

}
