package online.ronikier.todo.infrastructure.processor;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.infrastructure.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskProcessor {

    @Autowired
    private TaskService taskService;

    @Scheduled(fixedRate = 60000)
    public void processTasks() {
        log.info(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + Messages.SCHEDULER_TASK_PROCESSING);
        taskService.allTasks(SortOrder.NAME).parallelStream().forEach(System.out::println);
    }
}
