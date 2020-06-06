package online.ronikier.todo.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.templete.SuperForm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class PersonForm extends SuperForm {

    private Person person;

    private Iterable<Person> persons;

    private String knownByPersonId;

    @NotEmpty(message = Messages.FORM_PERSON_VALIDATION_USERNAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX, message = Messages.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX)
    public String getName() {
        return getPerson().getUsername();
    }

    public void setUsername(String username) {
        getPerson().setUsername(username);
    }

    public Person getPerson() {
        if (person == null) person = new Person();
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Iterable<Person> getPersons() {
        return persons;
    }

    public void setPersons(Iterable<Person> persons) {
        this.persons = persons;
    }

    public String getKnownByPersonId() {
        return knownByPersonId;
    }

    public void setKnownByPersonId(String knownByPersonId) {
        this.knownByPersonId = knownByPersonId;
    }

    @NotEmpty(message = Messages.FORM_PERSON_VALIDATION_USERNAME_NOT_EMPTY)
    @Size(max = Parameters.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX, message = Messages.FORM_PERSON_VALIDATION_USERNAME_SIZE_MAX)
    public String getUsername() {
        return getPerson().getUsername();
    }
}
