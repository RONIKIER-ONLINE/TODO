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

        StatusTask previousStatus = task.getStatusTask();

        if (task.getStart() != null && task.getStart().before(Utilities.dateCurrent())) {
            task.setStateTask(StateTask.STARTED);
        }

        if (task.getDue() != null) {
            if (task.getDue().before(Utilities.dateCurrent())) {
                task.setStatusTask(StatusTask.DELAYED);
            } else if (task.getDue().before(Utilities.dateFutureFrom(setupProcessorTaskApproachingDays,Utilities.dateCurrent()))) {
                task.setStatusTask(StatusTask.APPROACHING);
            } else {
                task.setStatusTask(StatusTask.OK);
            }
        } else {
            task.setStatusTask(StatusTask.UNKNOWN);
        }

        if (!task.getStatusTask().equals(previousStatus)) {
            taskStatusChanged(previousStatus,task);
        }

        taskService.saveTask(task);
        return task;
    }

    private void taskStatusChanged(StatusTask previousStatus, Task task) {
        log.warn(Messages.WARN_TASK_STATUS_CHANGED + Messages.SEPARATOR + task.getName() + Messages.SEPARATOR + (previousStatus != null ? previousStatus.getLabel() : "") + " > " + task.getStatusTask().getLabel());
    }
}
