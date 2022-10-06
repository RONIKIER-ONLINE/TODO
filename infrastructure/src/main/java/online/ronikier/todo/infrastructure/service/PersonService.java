package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.exception.PersonNotFoundException;
import online.ronikier.todo.domain.exception.PersonNotValidatedException;
import online.ronikier.todo.templete.SuperService;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface PersonService extends SuperService {

    /**
     *
     * @param personId
     * @return
     */
    Person findPersonById(Long personId) throws PersonNotFoundException;

    Person findPersonByUsername(String username) throws PersonNotFoundException;

    /**
     *
     * @param person
     */
    void savePerson(Person person);

    /**
     *
     * @param personId
     */
    void deletePersonById(Long personId);

    /**
     *
     * @return
     */
    Long countPersons();

    /**
     *
     * @return
     */
    List<Person> allPersons();

    /**
     *
     * @param personId
     * @return
     */
    List<Person> personsKnownPersons(Long personId);

    Person retrievePerson(String username, String password) throws PersonNotValidatedException;
}
