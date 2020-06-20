package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.templete.SuperService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
public interface TaskService extends SuperService {

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
    public List<Task> allTasks(SortOrder sortOrder);

    /**
     *
     * @return
     */
    public List<Task> filteredTasks(Task filterValues, SortOrder sortOrder);

    /**
     *
     * @return
     */
    List<Task> getMaintanceTasks();

    /**
     *
     * @param taskId
     * @return
     */
    List<Task> tasksRequiredTasks(Long taskId);
}
