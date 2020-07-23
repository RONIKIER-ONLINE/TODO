package online.ronikier.todo.interfaces.mappers;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.library.Parameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "created", target = "task.created", dateFormat = Parameters.WEB_FORM_DATE_FORMAT)
    @Mapping(source = "start", target = "task.start", dateFormat = Parameters.WEB_FORM_DATE_FORMAT)
    @Mapping(source = "due", target = "task.due", dateFormat = Parameters.WEB_FORM_DATE_FORMAT)
    @Mapping(source = "id", target = "task.id")
    @Mapping(source = "taskType", target = "task.taskType")
    @Mapping(source = "taskState", target = "task.taskState")
    @Mapping(source = "responsiblePerson.id", target = "responsiblePersonId")
    void domain2Form(Task task, @MappingTarget TaskForm taskForm);

    @Mapping(source = "task.start", target = "start", dateFormat = Parameters.WEB_FORM_DATE_FORMAT)
    @Mapping(source = "task.due", target = "due", dateFormat = Parameters.WEB_FORM_DATE_FORMAT)
    //@Mapping(source = "task.id", target = "id")
    @Mapping(ignore = true, target = "taskType")
    @Mapping(ignore = true, target = "taskState")
    @Mapping(ignore = true, target = "created")
    void form2Domain(TaskForm taskForm, @MappingTarget Task task);

}
