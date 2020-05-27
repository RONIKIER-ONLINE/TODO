package online.ronikier.todo.interfaces.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import online.ronikier.todo.Parameters;
import online.ronikier.todo.Messages;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class TaskForm {

    @NotNull(message = Messages.FORM_TASK_VALIDATION_IMPORTANT_NOT_NULL)
    private Boolean important;

    @NotNull(message = Messages.FORM_TASK_VALIDATION_URGENT_NOT_NULL)
    private Boolean urgent;

    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_CREATED_NOT_NULL)
    private String created;

    @NotNull(message = Messages.FORM_TASK_VALIDATION_START_NOT_NULL)
    private String start;

    @NotNull(message = Messages.FORM_TASK_VALIDATION_DUE_NOT_NULL)
    private String due;

    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_NAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_TASK_VALIDATION_NAME_SIZE_MAX, message = Messages.FORM_TASK_VALIDATION_NAME_SIZE_MAX)
    private String name;

    @NotEmpty(message = Messages.FORM_TASK_VALIDATION_DESCRIPTION_NOT_EMPTY)
    @Size(max = Parameters.FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX, message = Messages.FORM_TASK_VALIDATION_DESCRIPTION_SIZE_MAX)
    private String description;

}
