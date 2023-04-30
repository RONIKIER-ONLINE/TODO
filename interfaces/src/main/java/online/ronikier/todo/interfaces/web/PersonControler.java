package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.forms.PersonForm;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.interfaces.mappers.PersonMapper;
import online.ronikier.todo.templete.SuperController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Controller
@PropertySource("classpath:controller.properties")
@RequiredArgsConstructor
public class PersonControler extends SuperController {

    private final PersonService personService;

    private final PersonMapper personMapper;

    @Value("${person.completion.time.days:7}")
    private Integer personCompletionTimeDays;

    /**
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/person").setViewName("person");
        registry.addViewController("/person_delete").setViewName("person");
    }

    /**
     * @param personForm
     * @param model
     * @return
     */
    @GetMapping("person")
    public String person(PersonForm personForm, Model model) {
        if (!securityCheckOK(model)) return "/";
        initializeForm(personForm, model);
        return "person";
    }

    /**
     * @param personId
     * @param personForm
     * @param model
     * @return
     */
    @GetMapping("person/{personId}")
    public String personShow(@PathVariable(name = "personId", required = false) Long personId, PersonForm personForm, Model model) {
        if (!securityCheckOK(model)) return "/";
        Optional<Person> optionalPerson = personService.findPersonById(personId);
        if (!optionalPerson.isPresent()) {
            log.info(Messages.REPOSITORY_PERSON_NOT_FOUND + Messages.SEPARATOR + personId);
            return "person";
        }
        Person selectedPerson = optionalPerson.get();
        initializeForm(personForm, model);
        updateForm(personForm, selectedPerson);
        refreshForm(personForm, model);
        return "person";
    }



    /**
     * @param personForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("person")
    public String personProcess(@Valid PersonForm personForm, BindingResult bindingResult, Model model) {
        if (!securityCheckOK(model)) return "/";
        if (bindingResult.hasErrors()) {
            log.error(Messages.ERROR_PERSON_ADD);
            bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));
            initializeForm(personForm, model);
            return "person";
        }
        try {
            Person processedPerson = personService.findPersonByUsername(personForm.getUsername());
            if (processedPerson != null) {
                log.info((Messages.PERSON_EXISTS));
                log.debug(processedPerson.toString());
            } else {
                processedPerson = initializePerson(personForm);
            }
            savePerson(personForm, processedPerson);
            log.info(Messages.PERSON_CREATED);
            log.debug(processedPerson.toString());
        } catch (Exception e) {
            log.error(Messages.EXCEPTION_PERSON_CREATION + Messages.SEPARATOR + e.getMessage());
        }
        refreshForm(personForm, model);
        return "person";
    }

    private void savePerson(PersonForm personForm, Person person) {
        log.info(Messages.PERSON_MODIFIED);
        personMapper.form2Domain(personForm, person);
        personService.savePerson(person);
        if (personForm.getKnownByPersonId() != null && !personForm.getKnownByPersonId().equals("none")) {
            Optional<Person> knownByPersonOptional = personService.findPersonById(Long.valueOf(personForm.getKnownByPersonId()));
            if (!knownByPersonOptional.isPresent()) {
                log.error(Messages.ERROR_PERSON_DOES_NOT_EXIST);
            } else {
                Person knownByPerson = knownByPersonOptional.get();
                knownByPerson.knows(person);
                personService.savePerson(knownByPerson);
            }
        }

        log.info(Messages.PERSON_MODIFIED);
        log.debug(person.toString());
    }


    /**
     * @param personId
     * @param personForm
     * @param model
     * @return
     */
    @GetMapping("person_delete/{personId}")
    public String personDelete(@PathVariable(name = "personId", required = false) Long personId, PersonForm personForm, Model model) {
        log.info(Messages.PERSON_DELETING);
        personService.deletePersonById(personId);
        initializeForm(personForm, model);
        return "person";
    }

    /**
     * @param personForm
     * @return
     * @throws ParseException
     */
    private Person initializePerson(PersonForm personForm) throws ParseException {

        //TODO: Implement system common persons
        //return new Person(dafaultPersons,

        return new Person(ilPadre(),
                personForm.getUsername());
    }

    private List<Person> ilPadre() {
        return new ArrayList<>();
    }


    /**
     *
     * @param personId
     * @return
     */
    private Iterable<Person> getKnownPersonList(Long personId) {
        log.error(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + "Known person filtering");
        if (personId == null) return null;
        return personService.personsKnownPersons(personId);
    }

    /**
     *
     * @return
     */
    private Iterable<Person> getPersonList() {
        log.error(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + "Form person filtering");
        return personService.allPersons();
    }

    /**
     * @param personForm
     * @param model
     */
    private void initializeForm(PersonForm personForm, Model model) {
        personForm.setUsername("Don ");

        refreshForm(personForm,model);
    }

    /**
     *
     * @param personForm
     * @param model
     */
    private void refreshForm(PersonForm personForm, Model model) {
        model.addAttribute("personList", getPersonList());
        personForm.setPersons(getPersonList());
        model.addAttribute("personCount", personService.countPersons());
    }

    /**
     * @param targetPersonForm
     * @param sourcePerson
     */
    private void updateForm(PersonForm targetPersonForm, Person sourcePerson) {

        personMapper.domain2Form(sourcePerson, targetPersonForm);

        //targetPersonForm.setUsername(sourcePerson.getUsername());


    }

}
