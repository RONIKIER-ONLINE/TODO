package online.ronikier.todo.interfaces.mappers;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskForm domain2Form(Task task);

    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "start", target = "start", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "due", target = "due", dateFormat = "yyyy-MM-dd")
    Task form2Domain(TaskForm taskForm);

    void domain2Form(Task task, @MappingTarget TaskForm taskForm);

    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "start", target = "start", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "due", target = "due", dateFormat = "yyyy-MM-dd")
    void form2Domain(TaskForm taskForm, @MappingTarget Task task);

}
