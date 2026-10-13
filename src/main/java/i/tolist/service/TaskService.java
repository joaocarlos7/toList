package i.tolist.service;

import i.tolist.dto.request.TaskCreateRequest;
import i.tolist.dto.response.TaskResponse;
import i.tolist.entities.Task;
import i.tolist.entities.User;
import i.tolist.mappers.TaskMapper;
import i.tolist.repositories.TaskRepository;
import i.tolist.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.UUID;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    
    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }
    
    
    public TaskResponse create(TaskCreateRequest task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication.getName() == null) {
            throw new IllegalStateException("Username not found");
        }
        
        String userId = authentication.getName();
        UUID uuid = UUID.fromString(userId);
        
        User user = userRepository.findById(uuid)
                .orElseThrow(()-> new RuntimeException("User not found"));
        
        Task taskEntity = taskMapper.mapEntity(task);
        taskEntity.setUser(user);
        
        return taskMapper.mapRequest(taskRepository.save(taskEntity));
    }
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
    
    
}
