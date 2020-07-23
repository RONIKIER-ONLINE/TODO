package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.domain.forms.TaskFilterForm;
import online.ronikier.todo.templete.SuperService;

import java.util.List;
import java.util.Optional;

public interface TaskService extends SuperService {

    public void processComplete(Task processedTask);

    public Optional<Task> findTaskById(Long taskId);

    public Optional<Task> findTaskByName(String taskName);

    public void saveTask(Task task);

    public void deleteTaskById(Long taskId);

    public Long countTasks();

    public List<Task> allTasks(SortOrder sortOrder);

    public List<Task> filteredTasks(TaskFilterForm taskFilterForm, SortOrder sortOrder);

    List<Task> getMaintanceTasks();

    List<Task> tasksRequiredTasks(Long taskId);

    void processReject(Task processedTask);
}
