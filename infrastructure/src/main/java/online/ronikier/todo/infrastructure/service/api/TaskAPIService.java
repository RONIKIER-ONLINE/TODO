package online.ronikier.todo.infrastructure.service.api;

import online.ronikier.todo.domain.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskAPIService implements TaskAPI {

    @Override
    public ResponseEntity<String> addTask(Task task) {
        return null;
    }

    @Override
    public ResponseEntity<String> addTasks(List<Task> taskList) {
        return null;
    }

    @Override
    public ResponseEntity<List<Task>> getTasks() {
        return null;
    }

    @Override
    public ResponseEntity<Task> findTaskById(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<Task> findTaskByName(String taskName) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateTask(Task task) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteTask(Long taskId) {
        return null;
    }

}
