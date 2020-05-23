package online.ronikier.todo.infrastructure;

import online.ronikier.todo.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findByName(String name);
}
