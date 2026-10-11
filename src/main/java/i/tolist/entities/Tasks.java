package i.tolist.entities;

import i.tolist.entities.enums.TasksStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tasks {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tasksId")
    private UUID id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private TasksStatus status;
    
    @ManyToOne
    @JoinColumn(name = "userTask")
    private User user;
    
}
