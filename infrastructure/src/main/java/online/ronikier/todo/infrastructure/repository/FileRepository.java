package online.ronikier.todo.infrastructure.repository;

import online.ronikier.todo.domain.File;
import online.ronikier.todo.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository  extends CrudRepository<online.ronikier.todo.domain.File, Long> {
    List<online.ronikier.todo.domain.File> findByName(String name);
    List<online.ronikier.todo.domain.File> findByTaskId(Long taskId);
}
