package i.tolist.dto.request.auth;

import i.tolist.entities.enums.TasksPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskUpdateRequest (

            @NotBlank
            String title,

            @NotBlank
            String description,

            @NotNull
            TasksPriority tasksPriority,

            @NotNull
            boolean completed

    ) {
}

