package i.tolist.repositories;

import i.tolist.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, UUID> {
    Optional<Tasks> findByTitle(String title);
    Optional<Tasks> findById(UUID id);
}
