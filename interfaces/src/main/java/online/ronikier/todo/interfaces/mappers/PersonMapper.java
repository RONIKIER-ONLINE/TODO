package online.ronikier.todo.interfaces.mappers;

import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.forms.PersonForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonForm domain2Form(Person person);

    Person form2Domain(PersonForm personForm);

    void domain2Form(Person person, @MappingTarget PersonForm personForm);

    void form2Domain(PersonForm personForm, @MappingTarget Person person);

}
