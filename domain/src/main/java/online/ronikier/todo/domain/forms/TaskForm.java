package online.ronikier.todo.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.StateTask;
import online.ronikier.todo.domain.dictionary.TypeTask;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.SuperForm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TaskForm extends SuperForm {

    private Boolean showDialog;

    private Boolean showTasks;

    private Boolean clearDescription;

    private Task task;

    private Task filterTask;

    private String requiredByTaskId;

    private List<Task> tasks;

    private List<Task> requiredTasks;

    private List<Person> persons;

    private String responsiblePersonId;

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
            getTask().setCreated(Utilities.dateFromString(created));
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
            getTask().setStart(Utilities.dateFromString(start));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

//    @NotNull(message = Messages.FORM_TASK_VALIDATION_DUE_NOT_NULL)
    public String getDue() {
    return Utilities.stringFromDate(getTask().getDue());
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

    //TODO: Bust this ...
    public Task getTask() {
        if (task == null) task = new Task();
        return task;
    }

    public List<Task> getTasksRequiredTasks() {
        return task.getRequiredTasks();
    }

    public List<Task> getRequiredTasks() {
        return task.getRequiredTasks();
    }

    public StateTask getStateTask() {
        return getTask().getStateTask();
    }

    public TypeTask getTypeTask() {
        return getTask().getTypeTask();
    }

}
