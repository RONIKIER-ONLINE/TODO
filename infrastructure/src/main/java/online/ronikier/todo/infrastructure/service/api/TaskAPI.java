package online.ronikier.todo.infrastructure.service.api;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.templete.SuperService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskAPI {
    ResponseEntity<String> addTask(Task task);

    ResponseEntity<String> addTasks(List<Task> taskList);

    ResponseEntity<List<Task>> getTasks();

    ResponseEntity<Task> findTaskById(Long taskId);

    ResponseEntity<Task> findTaskByName(String taskName);

    ResponseEntity<String> updateTask(Task task);

    ResponseEntity<String> deleteTask(Long taskId);
}
