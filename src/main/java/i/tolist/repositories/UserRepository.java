package i.tolist.repositories;


import i.tolist.entities.User;
import i.tolist.entities.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional <User> findByRoleType(RoleType roleType);
    boolean existsByEmail(String email);
    
    
}
