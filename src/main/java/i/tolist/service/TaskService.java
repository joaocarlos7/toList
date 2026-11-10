package i.tolist.service;

import i.tolist.dto.request.auth.TaskCreateRequest;
import i.tolist.dto.request.auth.TaskUpdateRequest;
import i.tolist.dto.response.TaskResponse;
import i.tolist.entities.Task;
import i.tolist.entities.User;
import i.tolist.exception.TaskIdNotFound;
import i.tolist.mappers.TaskMapper;
import i.tolist.repositories.TaskRepository;
import i.tolist.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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


    @Transactional
    public TaskResponse findById(@RequestParam("id") UUID id) {
        if (taskRepository.existsById(id)) {
            return taskMapper.mapRequest(taskRepository.findById(id).get());
        } else  {
            throw new TaskIdNotFound(id);
        }
    }

    @Transactional
    public TaskResponse create(TaskCreateRequest task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User not authenticated");
        }
        
        String userId = authentication.getName();
        UUID uuid = UUID.fromString(userId);
        
        User user = userRepository.findById(uuid)
                .orElseThrow(()-> new RuntimeException("User not found"));
        
        Task taskEntity = taskMapper.mapEntity(task);
        taskEntity.setUser(user);
        
        return taskMapper.mapRequest(taskRepository.save(taskEntity));
    }

    @Transactional
    public TaskResponse update(UUID taskId, TaskUpdateRequest task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User not authenticated");
        }

        String userId = authentication.getName();
        UUID uuid = UUID.fromString(userId);

        Task taskEntity = taskRepository.findById(taskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Task not found"));

        if (!taskEntity.getUser().getId().equals(uuid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "User not authenticated");
        }

        taskEntity.setTitle(task.title());
        taskEntity.setDescription(task.description());
        taskEntity.setTasksPriority(task.tasksPriority());
        taskEntity.setCompleted(task.completed());


        return taskMapper.mapRequest(taskRepository.save(taskEntity));
    }

    @Transactional
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
    
    
}
