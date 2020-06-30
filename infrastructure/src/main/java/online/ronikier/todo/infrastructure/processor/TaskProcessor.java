package online.ronikier.todo.infrastructure.processor;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

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
        log.info(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + Messages.SCHEDULER_TASK_PROCESSING);

        taskService.allTasks(SortOrder.NONE).stream().forEach(this::checkDelayed);
    }
    public Task checkDelayed(Task task) {

        if (task.getTaskState().equals(TaskState.COMPLETED)) return task;

        TaskStatus previousStatus = task.getTaskStatus();

        if (task.getStart() != null && task.getStart().before(Utilities.dateCurrent())) {
            task.setTaskState(TaskState.STARTED);
        }

        if (task.getDue() != null) {
            if (task.getDue().before(Utilities.dateCurrent())) {
                task.setTaskStatus(TaskStatus.DELAYED);
            } else if (task.getDue().before(Utilities.dateFutureFrom(setupProcessorTaskApproachingDays,Utilities.dateCurrent()))) {
                task.setTaskStatus(TaskStatus.APPROACHING);
            } else if (task.getDue().before(Utilities.dateFutureFrom(0,Utilities.dateCurrent()))) {
                task.setTaskStatus(TaskStatus.TODAY);
            } else if (task.getDue().before(Utilities.dateFutureFrom(7,Utilities.dateCurrent()))) {
                task.setTaskStatus(TaskStatus.THIS_WEEK);
            } else {
                task.setTaskStatus(TaskStatus.OK);
            }
        } else {
            task.setTaskStatus(TaskStatus.UNKNOWN);
        }

        if (!task.getTaskStatus().equals(previousStatus)) {
            taskStatusChanged(previousStatus,task);
        }




        // TODO: Remove it
        if (task.getTaskState() == null) {
            task.setTaskState(TaskState.NEW);
        }

        if (task.getTaskType() == null) {
            task.setTaskType(TaskType.GENERAL);
        }
        if (task.getTaskState() == null) {
            task.setTaskState(TaskState.NEW);
        }

        if (task.getTaskStatus() == null) {
            task.setTaskStatus(TaskStatus.UNKNOWN);
        }
        // ===



        taskService.saveTask(task);
        return task;
    }

    private void taskStatusChanged(TaskStatus previousStatus, Task task) {
        log.warn(Messages.WARN_TASK_STATUS_CHANGED + Messages.SEPARATOR + task.getName() + Messages.SEPARATOR + (previousStatus != null ? previousStatus.getLabel() : "") + " > " + task.getTaskStatus().getLabel());
    }
}
