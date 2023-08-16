package io.task.inova.backend_task_inova.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StoryDTO {

    private UUID id;

    @Size(max = 20)
    private String title;

    @Size(max = 255)
    private String body;

    private UUID userId;

}
