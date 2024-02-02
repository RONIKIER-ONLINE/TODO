package online.ronikier.todo.domain.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.File;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.domain.dictionary.utility.LegendsRepository;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.FormAction;
import online.ronikier.todo.templete.SuperForm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Slf4j
@NoArgsConstructor
public class TaskForm extends SuperForm {

    String infoTaskStatus = LegendsRepository.taskStatusLegend();

    String infoTaskState = LegendsRepository.taskStateLegeng();

    private Task task = new Task(); // Needed for form fields // Needs work ;)

    private Long taskId;

    private Boolean showDialog;

    private Boolean showTaskDetails;

    private Boolean clearDescription;

    private TaskFilterForm taskFilterForm;

    private String requiredByTaskId;

    private List<Task> tasks;

    private Iterable<Task> requiredTasks;

    private List<File> files;

    private List<String> actions = Arrays
            .stream(FormAction.values())
            .map(Object::toString)
            .collect(Collectors.toList());

    private List<Person> persons;

    private String responsiblePersonId;

    @NotNull(message = Messages.FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL)
    public Boolean getImportant() {
        return task.getImportant();
    }

    public void setImportant(Boolean important) {
        task.setImportant(important);
    }

    @NotNull(message = Messages.FORM_TASK_VALIDATION_URGENT_NOT_NULL)
    public Boolean getUrgent() {
        return task.getUrgent();
    }

    public void setUrgent(Boolean urgent) {
        task.setUrgent(urgent);
    }

//    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_CREATED_NOT_NULL)
    public String getCreated() {
        return Utilities.stringFromDate(task.getCreated());
    }

    public void setCreated(String created) {
        try {
            task.setCreated(Utilities.dateFromString(created));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

//    @NotNull(message = Messages.FORM_TASK_VALIDATION_START_NOT_NULL)
    public String getStart() {
        return Utilities.stringFromDate(task.getStart());
    }

    public void setStart(String start) {
        try {
            task.setStart(Utilities.dateFromString(start));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

//    @NotNull(message = Messages.FORM_TASK_VALIDATION_DUE_NOT_NULL)
    public String getDue() {
    return Utilities.stringFromDate(task.getDue());
}

    public void setDue(String due) {
        try {
            task.setDue(Utilities.dateFromString(due));
        } catch (ParseException e) {
            log.error(Messages.EXCEPTION_TASK_DATE_PARSE + Messages.SEPARATOR + e.getMessage());
        }
    }

    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_NAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_TASK_VALIDATION_NAME_SIZE_MAX, message = Messages.FORM_TASK_VALIDATION_NAME_SIZE_MAX)
    public String getName() {
        return task.getName();
    }

    public void setName(String name) {
        task.setName(name);
    }

    //@NotEmpty(message = Messages.FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY)
    @Size(max = Parameters.FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX, message = Messages.FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX)

    public String getDescription() {
        return task.getDescription();
    }
    public void setDescription(String description) {
        task.setDescription(description);
    }
    public List<Task> getTasksRequiredTasks() {
        return task.getRequiredTasks();
    }
    public List<Task> getRequiredTasks() {
        return task.getRequiredTasks();
    }
    public TaskState getTaskState() {
        return task.getTaskState();
    }
    public TaskType getTaskType() {
        return task.getTaskType();
    }
    public Double getCostValue() {
        return task.getCostValue();
    }
    public void setCostValue(Double costValue) {
        task.setCostValue(costValue);
    }
    public CostUnit getCostUnit() {
        return task.getCostUnit();
    }
    public void setCostUnit(CostUnit costUnit) {
        task.setCostUnit(costUnit);
    }
    public TaskStatus getTaskStatus() {
        return task.getTaskStatus();
    }
    public void setTaskStatus(TaskStatus taskStatus) {
        task.setTaskStatus(taskStatus);
    }

    public Long getTaskId() {
        return taskId;
    }

    public Task getIsRequiredBy() {
        return task.getIsRequiredBy();
    }

    public void setIsRequiredBy(Task isRequiredBy) {
        task.setIsRequiredBy(isRequiredBy);
    }

    public List<Task> getIsRequiredByList() {
        return task.getIsRequiredByList();
    }

    public void setIsRequiredByList(List<Task> isRequiredByList) {
        task.setIsRequiredByList(isRequiredByList);
    }
}
