package online.ronikier.todo.interfaces.mappers;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper( TaskMapper.class );

    //@Mapping(source = "sourceMethodName", target = "targetMethodName")

    @Mappings({
            @Mapping(target="created", source = "created", dateFormat = "yyyy-MM-dd"),
            @Mapping(target="start", source = "start", dateFormat = "yyyy-MM-dd"),
            @Mapping(target="due", source = "due", dateFormat = "yyyy-MM-dd")})

    TaskForm domain2Form(Task task);
    Task form2Domain(TaskForm taskForm);

    void domain2Form(Task task, @MappingTarget TaskForm taskForm);
    void form2Domain(TaskForm taskForm, @MappingTarget Task task);

}
