package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.domain.exception.DataException;
import online.ronikier.todo.domain.exception.ParameterException;
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
public class TaskServiceGraph implements TaskService, DialogService {

    private final TaskRepository taskRepository;

    private String dialogMessage;

    public String kill() {
        return "Lou kills";
    }

    /**
     * @return new born task
     */
    @Override
    public Task initializeTask() {

        //TODO: Implement system common tasks

        return new Task(
                null,
                appendMaintanceTasks(),
                null,
                null,
                null,
                null,
                null,
                Utilities.dateCurrent(),
                null,       //Utilities.dateFuture(taskCompletionTimeDays),
                null,
                null,
                null,
                "chuj",
                Double.valueOf(1),
                CostUnit.SOLDIER,
                TaskState.ON_HOLD,
                TaskType.GENERAL,
                TaskStatus.OK
        );

    }

    /**
     * @return jak w temacie
     */
    private List<Task> appendMaintanceTasks() {
        if (Parameters.SYSTEM_SKIP_MAINTENANCE_TASKS) {
            log.info(Messages.SKIPPING_MAINTENANCE_TASKS);
            return null;
        }
        return getMaintanceTasks();
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

    @Override
    public List<Task> activeTasks() {
        return StreamSupport.stream(taskRepository.findAll()
                .spliterator(), true)
                .filter(task -> {

                    if (task.getTaskStatus() == null) return true;
                    if (task.getTaskState() == null) return true;

                    if (task.getTaskState() == TaskState.REJECTED) return false;
                    if (task.getTaskState() == TaskState.COMPLETED) return false;

                    if (task.getTaskStatus() == TaskStatus.DELAYED) return true;
                    if (task.getTaskStatus() == TaskStatus.TOMMOROW && task.getTaskState() != TaskState.STARTED) return true;
                    if (task.getTaskStatus() == TaskStatus.APPROACHING) return true;
                    if (task.getTaskState() == TaskState.ON_HOLD && task.getTaskStatus() != TaskStatus.OK) return true;

                    return false;

                })
                .collect(Collectors.toList());

    }

    @Override
    public Long countActiveTasks() {
        return Long.valueOf(activeTasks().size());
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

        Comparator<Task> taskComparator;

        switch (sortOrder) {
            case NONE: return StreamSupport.stream(taskRepository.findAll().spliterator(), true).collect(Collectors.toList());
            case DEFAULT :
            case REQUIRED_TASKS : {
                taskComparator = (Task tasksA, Task tasksB) -> tasksB.getRequiredTasks().size() - tasksA.getRequiredTasks().size();
                break;
            }
            case NAME :
            default:
                taskComparator = Comparator.comparing(Task::getName);
        }

        // Sorting cannot be done parallel
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false).sorted(taskComparator).collect(Collectors.toList());
        // otherwise use parallel for efficiency

    }

    @Override
    public List<Task> getMaintanceTasks() {
        // Implement individual task level tasks
        // One task set for alla ore task set each
        Task maintanceTaskA = null; //new Task(null, null,null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "A Maintance", "Maintance task A", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
        Task maintanceTaskB = null; //new Task(null, null,null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "B Maintance", "Maintance task B", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
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

    @Override
    public String processTaskAction(String action, String taskId) throws DataException, ParameterException {
        if (action == null && action.trim().length() == 0) {
            log.error(Messages.ERROR_PARAMETER_VALUE_EMPTY);
            throw new ParameterException(Messages.EXCEPTION_DIALOG_ACTION_NULL);
        }
        if (taskId == null) {
            throw new IllegalArgumentException(Messages.EXCEPTION_DIALOG_TASK_ID_NULL);
        }

        Optional<Task> taskOption = findTaskById(Long.valueOf(taskId));
        if (!taskOption.isPresent()) throw new DataException(Messages.ERROR_TASK_DOES_NOT_EXIST);
        Task task = taskOption.get();

        switch (action) {
            case "TODAY":
                task.setDue(Utilities.dateCurrent());
                task.setTaskStatus(TaskStatus.TODAY);
                task.setTaskState(TaskState.STARTED);
                task.setStart(Utilities.dateCurrent());
                break;
            case "TOMMOROW":
                task.setDue(Utilities.dateFuture(1));
                task.setTaskStatus(TaskStatus.TOMMOROW);
//                task.setTaskState(TaskState.ON_HOLD);
                break;
            case "NEXT_WEEK":
                task.setDue(Utilities.dateFuture(7));
                task.setTaskStatus(TaskStatus.THIS_WEEK);
//                task.setTaskState(TaskState.ON_HOLD);
                break;
            case "ON_HOLD":
                task.setTaskStatus(TaskStatus.OK);
                task.setTaskState(TaskState.ON_HOLD);
                break;
            case "REJECT":
                task.setTaskStatus(TaskStatus.OK);
                task.setTaskState(TaskState.REJECTED);
                log.info(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + Messages.TASK_STATE_REJECTED + task.getName());
                task.setStop(Utilities.dateCurrent());
                break;
            case "COMPLETE":
                task.setTaskStatus(TaskStatus.OK);
                task.setTaskState(TaskState.COMPLETED);
                log.info(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + Messages.TASK_STATE_COMPLETED + task.getName());
                task.setStop(Utilities.dateCurrent());
                break;
            default:
                dialogMessage = Messages.ERROR_DIALOG_ACTION + Messages.SEPARATOR + action + " action not known !!!";
        }

        saveTask(task);

        return dialogMessage;
    }

}
