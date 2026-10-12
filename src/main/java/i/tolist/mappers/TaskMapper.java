package i.tolist.mappers;

import i.tolist.dto.request.TaskCreateRequest;
import i.tolist.dto.response.TaskResponse;
import i.tolist.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    
    public TaskResponse mapRequest(Task taskEntity) {
        
        return new TaskResponse(
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getTasksPriority(),
                taskEntity.isCompleted()
        );
    }
    
    public Task mapEntity(TaskCreateRequest taskCreateRequest) {
        
        Task task = new Task();
        
        task.setTitle(taskCreateRequest.title());
        task.setDescription(taskCreateRequest.description());
        task.setTasksPriority(taskCreateRequest.tasksPriority());
        task.setCompleted(taskCreateRequest.completed());
        
        return task;
    }
}
