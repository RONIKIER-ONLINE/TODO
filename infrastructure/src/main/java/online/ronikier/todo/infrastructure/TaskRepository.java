package online.ronikier.todo.infrastructure;

import org.springframework.data.repository.CrudRepository;
import online.ronikier.todo.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findByName(String name);
}
