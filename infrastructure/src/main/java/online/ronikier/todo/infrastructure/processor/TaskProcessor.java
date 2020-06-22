package online.ronikier.todo.infrastructure.processor;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskProcessor {

    @Autowired
    private TaskService taskService;

    @Scheduled(fixedRate = 10000)
    public void processTasks() {
        log.info(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + Messages.SCHEDULER_TASK_PROCESSING);

        taskService.allTasks(SortOrder.NONE).stream().forEach(this::checkDelayed);
    }
    public Task checkDelayed(Task task) {
        if (task.getDue() != null) {
            if (task.getDue().before(Utilities.dateCurrent())) {
                task.setStatusTask(StatusTask.DELAYED);
            } else {
                task.setStatusTask(StatusTask.APPROACHING);
            }
        } else {
            task.setStatusTask(StatusTask.OK);
        }
        task.setCostUnit(CostUnit.PLN);
        task.setTypeTask(TypeTask.GENERAL);
        taskService.saveTask(task);
        return task;
    }
}
