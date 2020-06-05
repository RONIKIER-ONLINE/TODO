package online.ronikier.todo.domain.forms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TaskForm {

    private Task task;

    private String action;

    private String requiredByTaskId;

    private Iterable<Task> tasks;

    @NotNull(message = Messages.FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL)
    public Boolean getImportant() {
        return getTask().getImportant();
    }

    public void setImportant(Boolean important) {
        getTask().setImportant(important);
    }

    @NotNull(message = Messages.FORM_TASK_VALIDATION_URGENT_NOT_NULL)
    public Boolean getUrgent() {
        return getTask().getUrgent();
    }

    public void setUrgent(Boolean urgent) {
        getTask().setUrgent(urgent);
    }

//    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_CREATED_NOT_NULL)
    public String getCreated() {
        return Utilities.stringFromDate(getTask().getCreated());
    }

    public void setCreated(String created) {
        try {
            getTask().setDue(Utilities.dateFromString(created));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

//    @NotNull(message = Messages.FORM_TASK_VALIDATION_START_NOT_NULL)
    public String getStart() {
        return Utilities.stringFromDate(getTask().getStart());
    }

    public void setStart(String start) {
        try {
            getTask().setDue(Utilities.dateFromString(start));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

//    @NotNull(message = Messages.FORM_TASK_VALIDATION_DUE_NOT_NULL)
    public String getDue() {
        return Utilities.stringFromDate(getTask().getStart());
    }

    public void setDue(String due) {
        try {
            getTask().setDue(Utilities.dateFromString(due));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_NAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_TASK_VALIDATION_NAME_SIZE_MAX, message = Messages.FORM_TASK_VALIDATION_NAME_SIZE_MAX)
    public String getName() {
        return getTask().getName();
    }

    public void setName(String name) {
        getTask().setName(name);
    }

    //@NotEmpty(message = Messages.FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY)
    @Size(max = Parameters.FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX, message = Messages.FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX)
    public String getDescription() {
        return getTask().getDescription();
    }

    public void setDescription(String description) {
        getTask().setDescription(description);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRequiredByTaskId() {
        return requiredByTaskId;
    }

    public void setRequiredByTaskId(String requiredByTaskId) {
        this.requiredByTaskId = requiredByTaskId;
    }

    public Iterable<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Iterable<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTask() {
        if (task == null) task = new Task();
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
