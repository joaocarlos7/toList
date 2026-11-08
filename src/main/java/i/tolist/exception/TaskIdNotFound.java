package i.tolist.exception;

import java.util.UUID;

public class TaskIdNotFound extends RuntimeException {
    public TaskIdNotFound(UUID taskId) {
        super("Task with id " + taskId + " not found");
    }
}
