package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.exception.TaskStateException;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcesorServiceBatch implements ProcesorService {

    @Autowired
    private TaskService taskService;

    private boolean processing = false;

    @Override
    public void processTasks() {
        if (processing) {
            log.warn(Messages.TASK_PROCESSING_IN_PROGRESS);
            return;
        }
        processing = true;
        taskService.allTasks(SortOrder.NONE).stream().forEach(this::processTask);
        processing = false;
    }

    private Task processTask(Task task) {

        try {
            processTaskState(task);
            processTaskStatus(task);
            taskService.saveTask(task);
        } catch (Exception | TaskStateException e) {
            log.error(Messages.TASK_PROCESSING_HALT + Messages.SEPARATOR + e.getMessage());
            e.printStackTrace();
            processing = false;
        }

        return task;
    }

    private void processTaskStatus(Task task) {

        if (task.getTaskStatus() == null) {
            task.setTaskStatus(TaskStatus.OK);
        }

    }

    private void processTaskState(Task task) throws TaskStateException {

        if (task.getTaskState() == null) {
            task.setTaskState(TaskState.ON_HOLD);
        }

        if (task.getTaskStatus() == null) {
            task.setTaskStatus(TaskStatus.TODAY);
        }

        switch (task.getTaskState()) {
            case STARTED:

                if (task.getDue() == null) {
                    task.setDue(Utilities.dateMorning());
                }

                TaskStatus previousTaskStatus = task.getTaskStatus();

                if (task.getDue().before(Utilities.dateMidnight())) {
                    task.setTaskStatus(TaskStatus.DELAYED);
                }

                if (task.getDue().before(Utilities.dateFutureFrom(1, Utilities.dateMorning()))) {
                    if (task.getTaskStatus() != TaskStatus.DELAYED && task.getTaskStatus() != TaskStatus.TODAY)
                        task.setTaskStatus(TaskStatus.TOMMOROW);
                }

                if (task.getDue().before(Utilities.dateNextWeekend())) {
                    if (
                            task.getTaskStatus() != TaskStatus.DELAYED &&
                                    task.getTaskStatus() != TaskStatus.TOMMOROW
                    )
                        task.setTaskStatus(TaskStatus.NEXT_WEEKEND);
                }

                if (task.getDue().before(Utilities.dateNextMondayMorning())) {
                    if (
                            task.getTaskStatus() != TaskStatus.DELAYED &&
                            task.getTaskStatus() != TaskStatus.TOMMOROW &&
                            task.getTaskStatus() != TaskStatus.NEXT_WEEKEND
                    )
                        task.setTaskStatus(TaskStatus.NEXT_WEEK);
                }

                if (task.getDue().before(Utilities.dateNextMonth())) {
                    if (
                            task.getTaskStatus() != TaskStatus.DELAYED &&
                                    task.getTaskStatus() != TaskStatus.TOMMOROW &&
                                    task.getTaskStatus() != TaskStatus.NEXT_WEEKEND &&
                                    task.getTaskStatus() != TaskStatus.NEXT_WEEK
                    )
                        task.setTaskStatus(TaskStatus.NEXT_MONTH);
                }

//                if (task.getDue().after(Utilities.dateFutureFrom(0, Utilities.dateMorning()))) {
//                    if (task.getTaskStatus() != TaskStatus.DELAYED)
//                        task.setTaskStatus(TaskStatus.TODAY);
//                }

                if (!task.getTaskStatus().equals(previousTaskStatus)) {
                    taskStatusChanged(previousTaskStatus,task);
                }

                return;

            case ON_HOLD:
//                if (task.getStart() != null && task.getStart().before(Utilities.dateCurrent())) {
//                    log.info(Messages.TASK_PROCESSING_STARTED + Messages.SEPARATOR + task.getId());
//                    task.setTaskState(TaskState.ON_HOLD);
//                    task.setTaskStatus(TaskStatus.OK);
//                }
                return;
            case COMPLETED:
                task.setTaskStatus(TaskStatus.OK);
                return;
            case REJECTED:
                task.setTaskStatus(TaskStatus.OK);
                return;
            default: {
                log.warn(Messages.UNKNOWN_TASK_STATE + Messages.SEPARATOR + task.getTaskState());
                throw new TaskStateException(Messages.UNKNOWN_TASK_STATE + Messages.SEPARATOR + task.getTaskState());
            }
        }

    }

    private void taskStatusChanged(TaskStatus previousStatus, Task task) {
        try {
            log.info(Messages.TASK_STATUS_CHANGED + Messages.SEPARATOR +
                    task.getId() + Messages.SEPARATOR +
                    task.getName() + Messages.SEPARATOR +
                    (previousStatus != null ? previousStatus.getLabel() : "") + " > " + task.getTaskStatus().getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
