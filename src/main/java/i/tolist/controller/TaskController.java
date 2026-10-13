package i.tolist.controller;

import i.tolist.dto.request.TaskCreateRequest;
import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.response.TaskResponse;
import i.tolist.dto.response.UserResponse;
import i.tolist.entities.Task;
import i.tolist.repositories.TaskRepository;
import i.tolist.repositories.UserRepository;
import i.tolist.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")

public class TaskController {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;
    
    
    public TaskController(TaskRepository taskRepository, UserRepository userRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }
    
    
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> create(@RequestBody TaskCreateRequest taskCreateRequest) {
        
            TaskResponse savedTask = taskService.create(taskCreateRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedTask);
    }
    
}
