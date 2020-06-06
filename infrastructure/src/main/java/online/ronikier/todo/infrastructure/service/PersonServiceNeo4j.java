package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.infrastructure.repository.PersonRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceNeo4j implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public String kill() {
        return "Lou kills";
    }

    @Override
    @Cacheable("PERSONS_BY_ID")
    public Optional<Person> findPersonById(Long personId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING PERSON " + personId);
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) return personOptional;
        log.info((Messages.INFO_PERSON_NOT_FOUND + Messages.SEPARATOR + personId));
        return Optional.empty();
    }

    @Override
    @Cacheable("PERSONS_BY_USERNAME")
    public Person findPersonByUsername(String personUsername) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING PERSON " + Utilities.wrapString(personUsername));
        return personRepository.findByUsername(personUsername);
    }

    @Override
    public void savePerson(Person person) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "SAVING PERSON " + Utilities.wrapString(person.toString()));
        personRepository.save(person);
    }

    @Override
    public void deletePersonById(Long personId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "BUSTIN' PERSON " + personId);
        personRepository.deleteById(personId);
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "PERSON DELETED ...");

    }

    @Override
    public Long countPersons() {
        return personRepository.count();
    }

    @Override
    public Iterable<Person> allPersons() {
        return personRepository.findAll();
    }

    @Override
    public Iterable<Person> personsKnownPersons(Long personId) {
        Optional<Person> personsKnownPersons = findPersonById(personId);
        if (personsKnownPersons.isPresent()) {
            return personsKnownPersons.get().getKnownPersons();
        }
        return null;
    }

}
