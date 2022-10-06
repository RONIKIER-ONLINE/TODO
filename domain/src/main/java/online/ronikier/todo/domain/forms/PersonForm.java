package online.ronikier.todo.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.templete.SuperForm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class PersonForm extends SuperForm {

    private Person person;

    private Iterable<Person> persons;

    private String knownByPersonId;

    public Person getPerson() {
        if (person == null) person = new Person();
        return person;
    }

    @NotEmpty(message = Messages.FORM_PERSON_VALIDATION_USERNAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX, message = Messages.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX)
    public String getName() {
        return getPerson().getUsername();
    }

    public void setUsername(String username) {
        getPerson().setUsername(username);
    }

    @NotEmpty(message = Messages.FORM_PERSON_VALIDATION_USERNAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX, message = Messages.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX)
    public String getUsername() {
        return getPerson().getUsername();
    }

    public void setPassword(String password) {
        getPerson().setPassword(password);
    }

    @NotEmpty(message = Messages.FORM_PERSON_VALIDATION_PASSWORD_NOT_EMPTY)
    @Size(max = Parameters.FORM_PERSON_VALIDATION_PASSWORD_SIZE_MAX, message = Messages.FORM_PERSON_VALIDATION_PASSWORD_SIZE_MAX)
    public String getPassword() {
        return getPerson().getPassword();
    }
}
