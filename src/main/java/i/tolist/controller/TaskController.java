package i.tolist.controller;

import i.tolist.dto.request.auth.TaskCreateRequest;
import i.tolist.dto.request.auth.TaskUpdateRequest;
import i.tolist.dto.response.TaskResponse;
import i.tolist.repositories.TaskRepository;
import i.tolist.repositories.UserRepository;
import i.tolist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


    @GetMapping("/getId")
    public TaskResponse findById(@RequestParam("id") UUID id) {
        return taskService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> create(@RequestBody TaskCreateRequest taskCreateRequest) {
        
            TaskResponse savedTask = taskService.create(taskCreateRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedTask);
    }

    @PostMapping("/update/{taskId}")
    ResponseEntity<TaskResponse> update(@PathVariable UUID taskId, @RequestBody TaskUpdateRequest taskUpdatedRequest) {

        TaskResponse savedTask = taskService.update(taskId, taskUpdatedRequest);

        return ResponseEntity.ok(savedTask);


    }
    
}
