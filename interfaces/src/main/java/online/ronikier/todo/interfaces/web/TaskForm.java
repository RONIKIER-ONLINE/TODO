package online.ronikier.todo.interfaces.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import online.ronikier.todo.domain.Task;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
public class TaskForm {

    @NotNull
    private Boolean important;

    @NotNull
    private Boolean urgent;

    @NotNull
    private String created;

    @NotNull
    private String due;

    @NotNull
    @NotEmpty
    @Size(max=20)
    private String name;

    @NotNull
    @Size(max=200)
    private String description;

}
