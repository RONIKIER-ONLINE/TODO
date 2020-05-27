package online.ronikier.todo.interfaces.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import online.ronikier.todo.domain.Task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskForm {

    @NotNull
    private Boolean important;

    @NotNull
    private Boolean urgent;

    @NotNull
    @NotEmpty
    private String created;

    @NotNull
    @NotEmpty
    private String start;

    @NotNull
    @NotEmpty
    private String due;

    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String description;

}
