package online.ronikier.todo.infrastructure.repository;

import online.ronikier.todo.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<Task> findByName(String name);
    Optional<Task> findById(Long id);
}
