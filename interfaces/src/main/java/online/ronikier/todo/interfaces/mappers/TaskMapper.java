package online.ronikier.todo.interfaces.mappers;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface TaskMapper {


    @Mapping(source = "created", target = "task.created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "start", target = "task.start", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "due", target = "task.due", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "id", target = "task.id")
    TaskForm domain2Form(Task task);

    @Mapping(source = "task.created", target = "created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "task.start", target = "start", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "task.due", target = "due", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "task.id", target = "id")
    Task form2Domain(TaskForm taskForm);

    @Mapping(source = "created", target = "task.created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "start", target = "task.start", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "due", target = "task.due", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "id", target = "task.id")
    @Mapping(source = "stateTask", target = "task.stateTask")
    @Mapping(source = "typeTask", target = "task.typeTask")
    void domain2Form(Task task, @MappingTarget TaskForm taskForm);

    @Mapping(source = "task.created", target = "created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "task.start", target = "start", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "task.due", target = "due", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "task.stateTask", target = "stateTask")
    @Mapping(source = "task.typeTask", target = "typeTask")
    void form2Domain(TaskForm taskForm, @MappingTarget Task task);

}
