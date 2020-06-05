package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Task;

import java.util.Optional;
import java.util.Set;

/**
 *
 */
public interface TaskService {
    /**
     *
     * @return
     */
    public String kill();

    /**
     *
     * @param taskId
     * @return
     */
    public Optional<Task> findTaskById(Long taskId);

    /**
     *
     * @param taskName
     * @return
     */
    public Task findTaskByName(String taskName);

    /**
     *
     * @param task
     */
    public void saveTask(Task task);

    /**
     *
     * @param taskId
     */
    public void deleteTaskById(Long taskId);

    /**
     *
     * @return
     */
    public Long countTasks();

    /**
     *
     * @return
     */
    public Iterable<Task> allTasks();

    /**
     *
     * @param taskName
     * @return
     */
    Set<Task> getMaintanceTasks(String taskName);

    /**
     *
     * @param taskId
     * @return
     */
    Iterable<Task> tasksRequiredTasks(Long taskId);
}
