package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.domain.exception.TaskExistsException;
import online.ronikier.todo.domain.forms.TaskFilterForm;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceGraph implements TaskService {

    private final TaskRepository taskRepository;

    public String kill() {
        return "Lou kills";
    }

    @Override
    public void processComplete(Task processedTask) {
        processedTask.setTaskState(TaskState.COMPLETED);
        processedTask.setTaskStatus(TaskStatus.OK);
        saveTask(processedTask);
    }


    @Override
    public void processReject(Task processedTask) {
        processedTask.setTaskState(TaskState.REJECTED);
        processedTask.setDue(null);
        processedTask.setTaskStatus(TaskStatus.UNKNOWN);
        saveTask(processedTask);
    }

    @Override
    public Task processSave(Task task, String taskName) throws TaskExistsException {
        Optional<Task> processedTaskOptional = findTaskByName(taskName);
            if (processedTaskOptional.isPresent()) {
                log.info((Messages.TASK_EXISTS));
                log.debug(taskName);
                throw new TaskExistsException(taskName);
            }
        return task;
    }


    @Cacheable("TASKS_BY_ID")
    @Override
    public Optional<Task> findTaskById(Long taskId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING TASK " + taskId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) return taskOptional;
        log.info((Messages.TASK_NOT_FOUND + Messages.SEPARATOR + taskId));
        return Optional.empty();
    }

    @Cacheable("TASKS_BY_NAME")
    @Override
    public Optional<Task> findTaskByName(String taskName) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING TASK " + Utilities.wrapString(taskName));
        return taskRepository.findByName(taskName);
    }

    @Override
    @CacheEvict
    public void saveTask(Task task) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "SAVING TASK " + Utilities.wrapString(task.toString()));
        taskRepository.save(task);
    }

    @Override
    @CacheEvict
    public void deleteTaskById(Long taskId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "BUSTIN' TASK " + taskId);
        taskRepository.deleteById(taskId);
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "TASK DELETED ...");

    }

    @Override
    public Long countTasks() {
        return taskRepository.count();
    }

    @Override
    public List<Task> filteredTasks(TaskFilterForm taskFilterForm, SortOrder sortOrder) {

        if (taskFilterForm == null) return allTasks(sortOrder);

        Stream<Task> filteredTasksStream = allTasks(sortOrder).parallelStream();


        //TDOD: Refactor (but not too much ;)
        filteredTasksStream = filterSelected(taskFilterForm.getTaskType())
            ? filteredTasksStream.filter(task -> taskFilterForm.getTaskType().equals(task.getTaskType()))
            : filteredTasksStream;

        filteredTasksStream = filterSelected(taskFilterForm.getTaskState())
            ? filteredTasksStream.filter(task -> taskFilterForm.getTaskState().equals(task.getTaskState()))
            : filteredTasksStream;

        filteredTasksStream = filterSelected(taskFilterForm.getTaskStatus())
            ? filteredTasksStream.filter(task -> taskFilterForm.getTaskStatus().equals(task.getTaskStatus()))
            : filteredTasksStream;

        filteredTasksStream = filterFlag(taskFilterForm.getImportant())
            ? filteredTasksStream.filter(Task::getImportant)
            : filteredTasksStream;

        filteredTasksStream = filterFlag(taskFilterForm.getUrgent())
            ? filteredTasksStream.filter(Task::getUrgent)
            : filteredTasksStream;

        filteredTasksStream = filterSelected(taskFilterForm.getPhrase())
            ? filteredTasksStream.filter(task -> (
                task.getName().toUpperCase().contains(taskFilterForm.getPhrase().toUpperCase())
                ||
                task.getDescription().toUpperCase().contains(taskFilterForm.getPhrase().toUpperCase())))
            : filteredTasksStream;

        return filteredTasksStream.collect(Collectors.toList());

    }

    @Override
    @Cacheable("TASKS_SORTED")
    public List<Task> allTasks(SortOrder sortOrder) {

        Comparator<Task> taskComparator = (Task tasksA, Task tasksB) -> tasksA.getName().compareTo(tasksB.getName());

        switch (sortOrder) {
            case NONE: return StreamSupport.stream(taskRepository.findAll().spliterator(), true).collect(Collectors.toList());
            case DEFAULT :
            case REQUIRED_TASKS : {
                taskComparator = (Task tasksA, Task tasksB) -> tasksB.getRequiredTasks().size() - tasksA.getRequiredTasks().size();
                break;
            }
            case NAME :
            default:
        }

        // Sorting cannot be done parallel
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false).sorted(taskComparator).collect(Collectors.toList());
        // otherwise use parallel for efficiency

    }

    @Override
    public List<Task> getMaintanceTasks() {
        // Implement individual task level tasks
        // One task set for alla ore task set each
        Task maintanceTaskA = new Task(null, null,null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "A Maintance", "Maintance task A", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
        Task maintanceTaskB = new Task(null, null,null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "B Maintance", "Maintance task B", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
        return Arrays.asList(maintanceTaskA, maintanceTaskB);
    }

    @Override
    @Cacheable("TASKS_REQUIRED")
    public List<Task> tasksRequiredTasks(Long taskId) {

        Optional<Task> tasksRequiredTasks = findTaskById(taskId);
        if (tasksRequiredTasks.isPresent()) {
            return tasksRequiredTasks.get().getRequiredTasks();
        }
        return Collections.emptyList();
    }

    private boolean filterSelected(Object filter) {
        return Optional.ofNullable(filter).isPresent();
    }

    private boolean filterFlag(Boolean flag) {
        if (filterSelected(flag)) return flag;
        return false;
    }

}
