package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.templete.SuperService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    List<Person> allPersons();

    /**
     *
     * @param personId
     * @return
     */
    List<Person> personsKnownPersons(Long personId);

    Person getSuperPerson();
}
