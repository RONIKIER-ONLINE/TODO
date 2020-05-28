package online.ronikier.todo.infrastructure;

import online.ronikier.todo.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findByName(String name);
    Optional<Task> findById(Long id);
}
