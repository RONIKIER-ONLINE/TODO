package online.ronikier.todo.interfaces.web;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TaskForm {
    @NotNull
    @Size(min=1, max=30)
    private String name;

    @NotNull
    @Size(min=1, max=200)
    private String description;

}
