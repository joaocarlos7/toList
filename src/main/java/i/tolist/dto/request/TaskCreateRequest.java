package i.tolist.dto.request;

import i.tolist.entities.enums.TasksPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreateRequest (
        
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
