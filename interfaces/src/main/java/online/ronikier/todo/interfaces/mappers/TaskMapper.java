package online.ronikier.todo.interfaces.mappers;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.library.Parameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    final String DATE_FORMAT = Parameters.WEB_FORM_DATE_FORMAT;

    @Mapping(source = "created", target = "task.created", dateFormat = DATE_FORMAT)
    @Mapping(source = "start", target = "task.start", dateFormat = DATE_FORMAT)
    @Mapping(source = "due", target = "task.due", dateFormat = DATE_FORMAT)
    @Mapping(source = "id", target = "task.id")
    @Mapping(source = "stateTask", target = "task.stateTask")
    @Mapping(source = "typeTask", target = "task.typeTask")
    @Mapping(source = "responsiblePerson.id", target = "responsiblePersonId")
    void domain2Form(Task task, @MappingTarget TaskForm taskForm);

    @Mapping(source = "task.created", target = "created", dateFormat = DATE_FORMAT)
    @Mapping(source = "task.start", target = "start", dateFormat = DATE_FORMAT)
    @Mapping(source = "task.due", target = "due", dateFormat = DATE_FORMAT)
    //@Mapping(source = "task.id", target = "id")
    @Mapping(ignore = true, target = "stateTask")
    @Mapping(ignore = true, target = "typeTask")
    void form2Domain(TaskForm taskForm, @MappingTarget Task task);

}
