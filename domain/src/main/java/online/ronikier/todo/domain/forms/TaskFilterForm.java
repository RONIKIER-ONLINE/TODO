package online.ronikier.todo.domain.forms;

import lombok.Data;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.templete.SuperForm;

import java.util.Date;


@Data
public class TaskFilterForm extends SuperForm {

    private String phrase;
    private Boolean important;
    private Boolean urgent;
    private Date createdFrom;
    private Date createdTo;
    private Date startFrom;
    private Date startTo;
    private Date dueFrom;
    private Date dueTo;
    private TaskState taskState;
    private TaskStatus taskStatus;
    private TaskType taskType;

}
