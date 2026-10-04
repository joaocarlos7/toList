package i.tolist.entities;

import i.tolist.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tbUser")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    
    private UUID id;
    private String name;
    private String email;
    private String password;
    
    private boolean active;
    
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;
    
}
