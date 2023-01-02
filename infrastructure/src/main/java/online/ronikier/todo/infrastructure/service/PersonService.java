package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.exception.PersonNotFoundException;
import online.ronikier.todo.domain.exception.PersonNotValidatedException;
import online.ronikier.todo.infrastructure.repository.PersonRepository;
import online.ronikier.todo.library.Cryptonite;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.SuperService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService extends SuperService implements PersonInterface {

    private final PersonRepository personRepository;

    @Override
    @Cacheable("PERSONS_BY_ID")
    public Person findPersonById(Long personId) throws PersonNotFoundException {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING PERSON " + personId);
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) return personOptional.get();
        log.warn((Messages.INFO_PERSON_NOT_FOUND + Messages.SEPARATOR + personId));
        throw new PersonNotFoundException();
    }

    @Override
    @Cacheable("PERSONS_BY_USERNAME")
    public Person findPersonByUsername(String username) throws PersonNotFoundException {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING PERSON " + username);
        Optional<Person> personOptional = personRepository.findByUsername(username);
        if (personOptional.isPresent()) return personOptional.get();
        log.warn((Messages.INFO_PERSON_NOT_FOUND + Messages.SEPARATOR + username));
        throw new PersonNotFoundException();
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
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "COUNTING PERSONS");
        return personRepository.count();
    }

    @Override
    public List<Person> allPersons() {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "GETTING PERSONS");
        return StreamSupport.stream(personRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Person> personsKnownPersons(Long personId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "GETTING KNOWN PERSONS");
        Optional<Person> personsKnownPersons = personRepository.findById(personId);
        return personsKnownPersons.map(Person::getKnownPersons).orElse(null);
    }

    @Override
    public Person retrievePerson(String username, String password) throws PersonNotValidatedException {
        Optional<Person> leggedPersonOptional = personRepository.findByUsername(username);
        if (leggedPersonOptional.isPresent() && password.equals(Cryptonite.decode(leggedPersonOptional.get().getPassword()))) return leggedPersonOptional.get();
        throw new PersonNotValidatedException();
    }


}
