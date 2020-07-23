package online.ronikier.todo.infrastructure.processor;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskProcessor {

    @Value("${todo.setup.processor.task.approaching.days:1}")
    private Integer setupProcessorTaskApproachingDays;

    @Value("${todo.setup.neo4j.port:7687}")
    private String setupNeo4jPort;

    @Value("${todo.setup.neo4j.username:neo4j}")
    private String setupNeo4jUsername;

    @Value("${todo.setup.neo4j.password:secret}")
    private String setupNeo4jPassword;

    @Autowired
    private TaskService taskService;

    @Scheduled(fixedRate = 60000)
    public void processTasks() {
        log.info(Messages.SCHEDULER_TASK_PROCESSING);
        taskService.allTasks(SortOrder.NONE).stream().forEach(this::processTask);
    }

    private Task processTask(Task task) {
        processTaskState(task);
        processTaskStatus(task);
        taskService.saveTask(task);
        return task;
    }

    private void processTaskStatus(Task task) {

        TaskStatus previousTaskStatus = task.getTaskStatus();

        if (task.getDue() != null) {
            if      (task.getDue().before(Utilities.dateFutureFrom(1, Utilities.dateCurrent()))) task.setTaskStatus(TaskStatus.DELAYED);
            else if (task.getDue().before(Utilities.dateFutureFrom(setupProcessorTaskApproachingDays, Utilities.dateCurrent()))) task.setTaskStatus(TaskStatus.APPROACHING);
            else if (task.getDue().before(Utilities.dateFutureFrom(0, Utilities.dateCurrent()))) task.setTaskStatus(TaskStatus.TODAY);
            else task.setTaskStatus(TaskStatus.OK);
        }

        if (!task.getTaskStatus().equals(previousTaskStatus)) taskStatusChanged(previousTaskStatus,task);

    }

    private void processTaskState(Task task) {
        switch (task.getTaskState()) {
            case NEW:
            case STARTED: break;
            case ON_HOLD:
            case COMPLETED:
            case REJECTED: return;
            default: {
                log.warn(Messages.TASK_STATE_UNKNOWN + Messages.SEPARATOR + task.getTaskState());
                task.setTaskState(TaskState.UNKNOWN);
            }
        }

        if (task.getStart() != null && task.getStart().before(Utilities.dateCurrent()))
            task.setTaskState(TaskState.STARTED);

        if (task.getTaskState() == null)
            task.setTaskState(TaskState.NEW);

    }

    private void taskStatusChanged(TaskStatus previousStatus, Task task) {
        log.info(Messages.TASK_STATUS_CHANGED + Messages.SEPARATOR +
                task.getId() + Messages.SEPARATOR +
                task.getName() + Messages.SEPARATOR +
                (previousStatus != null ? previousStatus.getLabel() : "") + " > " + task.getTaskStatus().getLabel());
    }
}
