package online.ronikier.todo.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.exception.PersonNotFoundException;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.infrastructure.storage.StorageService;
import online.ronikier.todo.library.Cryptonite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements CommandLineRunner {

    private final PersonService personService;

    private final TaskService taskService;

    private final StorageService storageService;

    @Override
    public void run(String... args) throws Exception {

        log.info(Messages.INFO_INITIALISING_DATABASE);

        try {

            taskService.clearTasks();

            storageService.deleteAll();
            storageService.init();

            personService.findPersonByUsername("ADMIN");

        } catch (PersonNotFoundException personNotFoundException) {
            personService.savePerson(Person.builder().password(Cryptonite.encode("ADMIN")).username("ADMIN").build());
        }
    }
}

