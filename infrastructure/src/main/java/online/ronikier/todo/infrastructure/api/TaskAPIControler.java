package online.ronikier.todo.infrastructure.api;

import lombok.RequiredArgsConstructor;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.service.api.TaskAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskAPIControler {

    private TaskAPI taskAPI;

    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        return taskAPI.addTask(task);
    }

    @PostMapping("/add_list")
    public ResponseEntity<String> addTasks(@RequestBody List<Task> taskList) {
        return taskAPI.addTasks(taskList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks() {
        return taskAPI.getTasks();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long taskId) {
        return taskAPI.findTaskById(taskId);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Task> findTaskByName(@PathVariable String taskName) {
        return taskAPI.findTaskByName(taskName);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        return taskAPI.updateTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        return taskAPI.deleteTask(taskId);
    }

    @ExceptionHandler
    public ResponseEntity<String> error() {
        ResponseEntity<String> responseEntity = new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        return responseEntity;
    }

}
