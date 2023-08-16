package io.task.inova.backend_task_inova.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private UUID id;

    @Size(max = 255)
    private String name;

    private LocalDateTime birthdate;

}
