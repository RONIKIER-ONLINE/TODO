package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Person;
import online.ronikier.todo.templete.SuperService;

import java.util.List;
import java.util.Optional;

public interface PersonService extends SuperService {

    Optional<Person> findPersonById(Long personId);

    Person findPersonByUsername(String personUsername);

    void savePerson(Person person);

    void deletePersonById(Long personId);

    Long countPersons();

    List<Person> allPersons();

    List<Person> personsKnownPersons(Long personId);

    Person getSuperPerson();

}
