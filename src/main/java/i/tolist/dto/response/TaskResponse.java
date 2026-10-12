package i.tolist.dto.response;

import i.tolist.entities.enums.TasksPriority;

public record TaskResponse (
        
        String title,
        String description,
        TasksPriority tasksPriority,
        boolean completed
) {
}
