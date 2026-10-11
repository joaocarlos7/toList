package i.tolist.service;

import i.tolist.entities.Tasks;
import i.tolist.repositories.TasksRepository;
import i.tolist.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TasksService {
    
    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;
    
    public TasksService(TasksRepository tasksRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }
    
    
    public Tasks createTask(Tasks tasks) {
        return tasksRepository.save(tasks);
    }
    
    
}
