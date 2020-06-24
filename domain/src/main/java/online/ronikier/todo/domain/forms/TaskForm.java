package online.ronikier.todo.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
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

    private Boolean showTaskDetails;

    private Boolean clearDescription;

    private Task task;

    private TaskFilterForm taskFilterForm;

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

    //TODO: Bust this ...
    public Task getTask() {
        if (task == null) task = new Task();
        return task;
    }
    public String getDescription() {
        return getTask().getDescription();
    }
    public void setDescription(String description) {
        getTask().setDescription(description);
    }
    public List<Task> getTasksRequiredTasks() {
        return getTask().getRequiredTasks();
    }
    public List<Task> getRequiredTasks() {
        return getTask().getRequiredTasks();
    }
    public TaskState getTaskState() {
        return getTask().getTaskState();
    }
    public TaskType getTaskType() {
        return getTask().getTaskType();
    }
    public Double getCostValue() {
        return getTask().getCostValue();
    }
    public void setCostValue(Double costValue) {
        getTask().setCostValue(costValue);
    }
    public CostUnit getCostUnit() {
        return getTask().getCostUnit();
    }
    public void setCostUnit(CostUnit costUnit) {
        getTask().setCostUnit(costUnit);
    }
    public TaskStatus getTaskStatus() {
        return getTask().getTaskStatus();
    }
    public void setTaskStatus(TaskStatus taskStatus) {
        getTask().setTaskStatus(taskStatus);
    }
}
