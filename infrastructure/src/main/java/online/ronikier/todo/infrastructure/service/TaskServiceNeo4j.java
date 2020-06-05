package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceNeo4j implements TaskService {

    private final TaskRepository taskRepository;

    public String kill() {
        return "Lou kills";
    }

    @Cacheable("TASKS_BY_ID")
    public Optional<Task> findTaskById(Long taskId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING TASK " + taskId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) return taskOptional;
        log.info((Messages.INFO_TASK_NOT_FOUND + Messages.SEPARATOR + taskId));
        return Optional.empty();
    }

    @Cacheable("TASKS_BY_NAME")
    public Task findTaskByName(String taskName) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING TASK " + Utilities.wrapString(taskName));
        return taskRepository.findByName(taskName);
    }

    public void saveTask(Task task) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "SAVING TASK " + Utilities.wrapString(task.toString()));
        taskRepository.save(task);
    }

    public void deleteTaskById(Long taskId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "BUSTIN' TASK " + taskId);
        taskRepository.deleteById(taskId);
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "TASK DELETED ...");

    }

    public Long countTasks() {
        return taskRepository.count();
    }

    public Iterable<Task> allTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Set<Task> getMaintanceTasks(String taskName) {
        //TODO: Implement individual task level tasks
        Task maintanceTaskA = new Task(null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), taskName + " A Maintance", "Maintance task A for " + taskName);
        Task maintanceTaskB = new Task(null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), taskName + " B Maintance", "Maintance task B for " + taskName);
        List<Task> maintanceTasks = Arrays.asList(maintanceTaskA,maintanceTaskB);
        return new HashSet<>(maintanceTasks);
    }

    @Override
    public Iterable<Task> tasksRequiredTasks(Long taskId) {
        Optional<Task> tasksRequiredTasks = findTaskById(taskId);
        if (tasksRequiredTasks.isPresent()) {
            return tasksRequiredTasks.get().getRequiredTasks();
        }
        return null;
    }
}
