package io.task.inova.backend_task_inova.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewDTO {

    private UUID id;

    private Integer rating;

    @NotNull
    @Size(max = 255)
    private String comment;

    private UUID storyId;

    private UUID userId;

}
