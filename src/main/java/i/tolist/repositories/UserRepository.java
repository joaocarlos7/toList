package i.tolist.repositories;


import i.tolist.entities.User;
import i.tolist.entities.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional <User> findByRoleType(RoleType roleType);
    boolean existsByEmail(String email);
    
    
}
