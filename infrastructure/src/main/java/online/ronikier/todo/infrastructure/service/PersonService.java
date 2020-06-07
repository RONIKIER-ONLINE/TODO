package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.templete.SuperService;

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
    Optional<Person> findPersonById(Long personId);

    /**
     *
     * @param personUsername
     * @return
     */
    Person findPersonByUsername(String personUsername);

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
    Iterable<Person> allPersons();

    /**
     *
     * @param personId
     * @return
     */
    Iterable<Person> personsKnownPersons(Long personId);

    Person getSuperPerson();
}
