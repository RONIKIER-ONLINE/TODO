package online.ronikier.todo.processor;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.exception.TaskStateException;
import online.ronikier.todo.infrastructure.service.ProcesorService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskProcessor {

    @Autowired
    ProcesorService procesorService;

//    @Scheduled(fixedRate = 10000)
    @Scheduled(fixedRate = 3600000)
    public void processTasks() {
        log.info(Messages.SCHEDULER_TASK_PROCESSING);
        procesorService.processTasks();
    }

}
